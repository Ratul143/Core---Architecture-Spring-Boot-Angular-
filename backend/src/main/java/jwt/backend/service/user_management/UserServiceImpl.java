package jwt.backend.service.user_management;

import jwt.backend.constant.CustomMessage;
import jwt.backend.constant.StatusCode;
import jwt.backend.constant.Topics;
import jwt.backend.dto.ChangePasswordDto;
import jwt.backend.dto.common.ValidationDto;
import jwt.backend.dto.request.auth.LoginRequest;
import jwt.backend.dto.request.auth.UserCreateRequest;
import jwt.backend.dto.request.auth.UserUpdateRequest;
import jwt.backend.dto.response.CommonResponse;
import jwt.backend.dto.response.LoginResponse;
import jwt.backend.entity.user_management.Role;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import jwt.backend.exception.NotFoundException;
import jwt.backend.exception.user_management.EmailNotFoundException;
import jwt.backend.repository.user_management.AccsAuthUserRepository;
import jwt.backend.repository.user_management.AccsCustomerViewRepository;
import jwt.backend.repository.user_management.AllLookupRepository;
import jwt.backend.repository.user_management.RoleRepository;
import jwt.backend.security.entity.UserPrincipalV2;
import jwt.backend.security.utility.JWTTokenProvider;
import jwt.backend.service.CommonService;
import jwt.backend.service.EmailService;
import jwt.backend.service.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static jwt.backend.constant.AppConstant.NO_USER_FOUND_BY_EMAIL;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;

    private final AccsAuthUserRepository accsAuthUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthService authService;
    private final RoleService roleService;
    private final CommonService commonService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserValidation userValidation;
    private final AccsCustomerViewRepository accsCustomerViewRepository;
    private final AllLookupRepository allLookupRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LoginResponse signIn(LoginRequest request, HttpServletRequest httpServletRequest) {

        // Validate request parameters
        if (StringUtils.isBlank(request.getUsername())) {
            throw new IllegalArgumentException("Username is empty");
        }
        if (StringUtils.isBlank(request.getPassword())) {
            throw new IllegalArgumentException("Password is empty");
        }

        // Check if user exists and is active
        Accs_Auth_User accsAuthUser = accsAuthUserRepository.findByUsernameAndIsDeleted(request.getUsername(), false)
                .orElseThrow(() -> new UsernameNotFoundException("User not found or inactive"));

        if (!accsAuthUser.isEnabled()) {
            throw new DisabledException("You are not an active user, Please contact admin for more details");
        }

        try {
//            String encodePasswordToSHA256 = authService.encodePasswordSha256(request.getPassword());

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserPrincipalV2 userDetails = (UserPrincipalV2) authentication.getPrincipal();

            String jwt = jwtTokenProvider.generateJwtTokenV2(userDetails);

            log.info("Inside login method and login success!");
            commonService.recentVisitors(httpServletRequest, accsAuthUser, "LOGIN");
            return new LoginResponse(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getFullName(),
                    userDetails.getEmail(),
                    jwt,
                    userDetails.getRole());
        } catch (LockedException e) {
            log.info("Inside login method and login failed due to account lock.");
            throw new LockedException("User account is locked. Please contact admin for assistance.");
        } catch (AuthenticationException e) {
            log.info("Inside login method and login failed due to exception");
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public CommonResponse addUser(UserCreateRequest request) {

        ValidationDto dto = userValidation.validateCreateRequest(request);
        if (!dto.isValid()) {
            return new CommonResponse(StatusCode.NO_CONTENT, dto.getMessage() + " is required");
        }

        Accs_Auth_User user = new Accs_Auth_User();

        if(accsAuthUserRepository.existsByIsDeletedAndEmail(false, request.getEmail())) {
            return new CommonResponse(StatusCode.BAD_REQUEST, "Email is already in use!");
        }else if(accsAuthUserRepository.existsByIsDeletedAndUsername(false, request.getUsername())) {
            return new CommonResponse(StatusCode.BAD_REQUEST, "Username is already in use!");
        }

        //Validate and set Roles
        Optional<Role> role = roleRepository.findByIdAndDeletedAt(request.getRoleId(), null);
        if (role.isPresent()) {
            user.setRole(role.get());
        } else {
            return new CommonResponse(StatusCode.NO_CONTENT, "Role not found, please select a valid role");
        }

        //Set Other properties
        String password = generatePassword();

        user.setPassword(encodedPassword(password));
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setCellNo(request.getCellNo());
        user.setSignature(request.getSignature());
        user.setAccountLocked(request.isAccountLocked());
        user.setEnabled(request.isEnabled());
        user.setCountryName(request.getCountryName());
        user.setEmployeeId(request.getEmployeeId());
        user.setCreatedBy(authService.getAuthUser().getFullName());


        try {
            accsAuthUserRepository.save(user);
            emailService.sendNewPasswordEmail(user.getUsername(), user.getFullName(), password, user.getEmail(), user.getRole().getRole());
            log.info("Inside User service addUser method and user added successfully! ");
            return new CommonResponse(StatusCode.CREATED, Topics.USER.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE);
        } catch (DataAccessException e) {
            log.info("Inside User service addUser method and user creation failed due to Exception: {}", e.getMessage());
            return new CommonResponse(StatusCode.BAD_REQUEST, CustomMessage.SAVE_FAILED_MESSAGE);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CommonResponse updateUserInfo(UserUpdateRequest request) {

        ValidationDto dto = userValidation.validateUpdateRequest(request);
        if (!dto.isValid()) {
            return new CommonResponse(StatusCode.NO_CONTENT, dto.getMessage() + " is required");
        }

        Accs_Auth_User user;

        Optional<Accs_Auth_User> optionalUser = accsAuthUserRepository.findByIsDeletedAndId(false, request.getId());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return new CommonResponse(StatusCode.NOT_FOUND, "User not found!");
        }

        if (!request.getEmail().equalsIgnoreCase(user.getEmail())) {
            if (accsAuthUserRepository.existsByIsDeletedAndEmail(false, request.getEmail())) {
                return new CommonResponse(StatusCode.BAD_REQUEST, "Email is already in use!");
            }
        }
        if (!request.getUsername().equalsIgnoreCase(user.getUsername())) {
            if (accsAuthUserRepository.existsByIsDeletedAndUsername(false, request.getUsername())) {
                return new CommonResponse(StatusCode.BAD_REQUEST, "Username is already in use!");
            }
        }

        //Validate and set Roles
        Optional<Role> role = roleRepository.findByIdAndDeletedAt(request.getRoleId(), null);
        if (role.isPresent()) {
            user.setRole(role.get());
        } else {
            return new CommonResponse(StatusCode.NO_CONTENT, "Role not found, please select a valid role");
        }

        //Set Other properties

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setCellNo(request.getCellNo());
        user.setSignature(request.getSignature());
        user.setAccountLocked(request.isAccountLocked());
        user.setEnabled(request.isEnabled());
        user.setCountryName(request.getCountryName());
        user.setEmployeeId(request.getEmployeeId());

        user.setModifiedBy(authService.getAuthUser().getFullName());
        user.setModifiedAt(new Date());


        try {
            accsAuthUserRepository.save(user);
            emailService.sendNewPasswordEmail(user.getUsername(), user.getFullName(), user.getPassword(), user.getEmail(), user.getRole().getRole());
            log.info("Inside User service addUser method and user added successfully! ");
            return new CommonResponse(StatusCode.CREATED, Topics.USER.getName() + CustomMessage.UPDATE_SUCCESS_MESSAGE);
        } catch (DataAccessException e) {
            log.info("Inside User service addUser method and user creation failed due to Exception: {}", e.getMessage());
            return new CommonResponse(StatusCode.BAD_REQUEST, CustomMessage.UPDATE_FAILED_MESSAGE);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CommonResponse deleteUserProfile(Long id) {
//        UserPrincipalV2 userPrincipalV2 = (UserPrincipalV2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (!userPrincipalV2.getId().equals(id)) {
//            log.info("Inside User service delete method and updated failed due to permission error!");
//            return new CommonResponse(StatusCode.UNAUTHORIZED, CustomMessage.DELETE_PERMISSION_ERROR);
//        }

        try {
            Optional<Accs_Auth_User> userToDelete = accsAuthUserRepository.findById(id);
            if (userToDelete.isEmpty()) {
                log.error("User not found with ID: {}", id);
                return new CommonResponse(StatusCode.NOT_FOUND, CustomMessage.NO_RECORD_FOUND);
            }

            Accs_Auth_User accsAuthUser = userToDelete.get();
            accsAuthUser.setIsDeleted(true);
            accsAuthUser.setEnabled(false);
            accsAuthUser.setDeletedAt(commonService.getCurrentDateTime());
            accsAuthUser.setDeletedById(authService.getAuthUserId());
            accsAuthUserRepository.save(accsAuthUser);
            log.info("Inside User service delete method and deleted successfully");
            return new CommonResponse(StatusCode.CREATED, Topics.USER.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE);
        } catch (DataAccessException e) {
            log.info("Inside User service delete method and delete failed due to Exception: {}", e.getMessage());
            return new CommonResponse(StatusCode.BAD_REQUEST, Topics.USER.getName() + CustomMessage.DELETE_FAILED_MESSAGE);
        }
    }

    @Override
    public CommonResponse resetPassword(String email) throws EmailNotFoundException {
        try {
            Accs_Auth_User accsAuthUser = accsAuthUserRepository.findUserByEmail(email);
            if (accsAuthUser == null) {
                throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
            }
            String password = generatePassword();
            accsAuthUser.setPassword(encodedPassword(password));
            accsAuthUserRepository.save(accsAuthUser);
            emailService.sendNewPasswordEmail(accsAuthUser.getUsername(), accsAuthUser.getFullName(), password, accsAuthUser.getEmail(), accsAuthUser.getRole().getRole());
            log.info("Password reset for user with email: {}", email);
            return new CommonResponse(StatusCode.OK, CustomMessage.RESET_PASSWORD_MESSAGE);
        } catch (EmailNotFoundException e) {
            log.error("Email not found: {}", email);
            throw e;
        } catch (Exception e) {
            log.error("Error resetting password for user with email: {}", email, e);
            throw new RuntimeException("Failed to reset password. Please try again later.");
        }
    }

    private String encodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    @Override
    public Long count() {
        return accsAuthUserRepository.countAllByDeletedAtNull();
    }

    @SneakyThrows
    private void checkIfResponsiblePerson(Long responsibleTo) {
        if (!(responsibleTo > 0)) {
            throw new NotFoundException("Responsible person can't left blank!");
        }
    }

    @Override
    public CommonResponse changeUserPassword(ChangePasswordDto changePasswordDto) {
        UserPrincipalV2 userPrincipalV2 = (UserPrincipalV2) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!userPrincipalV2.getId().equals(changePasswordDto.getUserId())) {
            log.info("Inside User service Change Password method and updated failed due to permission error!");
            return new CommonResponse(StatusCode.UNAUTHORIZED, CustomMessage.UPDATE_PERMISSION_ERROR);
        }

        try {
            Optional<Accs_Auth_User> userEntityOptional = accsAuthUserRepository.findByIdAndIsDeleted(changePasswordDto.getUserId(), false);
            if (userEntityOptional.isEmpty()) {
                log.info("Inside User service Change Password method and no record found by ID: {}", changePasswordDto.getUserId());
                return new CommonResponse(StatusCode.BAD_REQUEST, CustomMessage.NO_RECORD_FOUND + changePasswordDto.getUserId());
            }

            Accs_Auth_User accsAuthUser = userEntityOptional.get();
            accsAuthUser.setModifiedAt(commonService.getCurrentDateTime());

            if (changePasswordDto.getCurrentPassword() != null && passwordEncoder.matches(changePasswordDto.getCurrentPassword(), accsAuthUser.getPassword())) {
                accsAuthUser.setPassword(encodedPassword(changePasswordDto.getNewPassword()));
                log.info("Inside User Service change password and password has been matched");
            } else {
                return new CommonResponse(StatusCode.BAD_REQUEST, "Password doesn't match");
            }

            accsAuthUserRepository.save(accsAuthUser);
            log.info("Inside User service Change Password method and updated successfully!");
            return new CommonResponse(StatusCode.CREATED, "Password " + CustomMessage.UPDATE_SUCCESS_MESSAGE);
        } catch (NoSuchElementException e) {
            log.error("User not found with ID: {}", changePasswordDto.getUserId());
            return new CommonResponse(StatusCode.BAD_REQUEST, CustomMessage.NO_RECORD_FOUND + changePasswordDto.getUserId());
        } catch (AuthenticationException e) {
            log.error("Password doesn't match for user with ID: {}", changePasswordDto.getUserId());
            return new CommonResponse(StatusCode.BAD_REQUEST, "Password doesn't match");
        } catch (Exception e) {
            log.error("Error changing password: {}", e.getMessage());
            throw new DataIntegrityViolationException("Password " + CustomMessage.UPDATE_FAILED_MESSAGE);
        }
    }

    @SneakyThrows
    private void checkIfReportingEmpty(Long reporting) {
        if (reporting == null) {
            throw new NotFoundException("Reporting can't be empty");
        }
    }

}
