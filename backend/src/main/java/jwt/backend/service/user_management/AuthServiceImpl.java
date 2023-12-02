package jwt.backend.service.user_management;

import jwt.backend.dto.user_management.AccsUserListDto;
import jwt.backend.dto.user_management.Passwords;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import jwt.backend.exception.user_management.UserNotFoundException;
import jwt.backend.repository.user_management.AccsAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccsAuthUserRepository accsAuthUserRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long getAuthUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Accs_Auth_User user = accsAuthUserRepository.findUserByUsername((String) authentication.getPrincipal());
        if (user == null) {
            try {
                throw new UserNotFoundException("Please logout & login again!");
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return user.getId();
    }

    @Override
    public Accs_Auth_User getAuthUser() {
        return accsAuthUserRepository.findByIdAndDeletedAtNull(getAuthUserId());
    }

    @Override
    public Accs_Auth_User findUserByUsername(String username) {
        return accsAuthUserRepository.findUserByUsername(username);
    }

    //---------------------------------------------------------------- ACCS_AUTH_USER STARTS IN HERE----------------------------------------------------------------

    @Override
    public Passwords encodePasswordSha256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal string representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the hashed password and the original plain text password
            return new Passwords(hexString.toString(), password);
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception accordingly
            log.error("Error occurred while encoding the password with SHA-256: {}", e.getMessage());
        }
        return null; // Return null in case of an error
    }

    @Override
    public Accs_Auth_User findUserById(Long id) {
        return accsAuthUserRepository.findByIdAndIsDeletedFalseAndEnabledTrueAndAccountLockedFalse(id);
    }

    @Override
    public Page<AccsUserListDto> authUserList(
            String searchValueForUsername,
            String searchValueForEmail,
            String searchValueForUserType,
            String searchValueForCellNo,
            Integer page,
            Integer size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccsUserListDto> query = cb.createQuery(AccsUserListDto.class);
        Root<Accs_Auth_User> root = query.from(Accs_Auth_User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchValueForUsername != null && !searchValueForUsername.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("username")), "%" + searchValueForUsername.toLowerCase() + "%"));
        }
        if (searchValueForEmail != null && !searchValueForEmail.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + searchValueForEmail.toLowerCase() + "%"));
        }
        if (searchValueForUserType != null && !searchValueForUserType.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("userType").get("title")), "%" + searchValueForUserType.toLowerCase() + "%"));
        }
        if (searchValueForCellNo != null && !searchValueForCellNo.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("cellNo")), "%" + searchValueForCellNo.toLowerCase() + "%"));
        }

        predicates.add(cb.isTrue(root.get("enabled")));

        query.select(cb.construct(
                        AccsUserListDto.class,
                        root.get("id"),
                        root.get("username"),
                        root.get("fullName"),
                        root.get("email"),
//                        root.get("role").get("role"),
                        root.get("employeeId"),
                        root.get("enabled"),
                        root.get("cellNo"),
                        root.get("accountLocked")
                ))
                .where(predicates.toArray(new Predicate[0]))
                .orderBy(cb.desc(root.get("createdAt")));

        List<AccsUserListDto> userDtoList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long totalCount = getTotalUserCount(predicates);

        return new PageImpl<>(userDtoList, pageable, totalCount);
    }

    private long getTotalUserCount(List<Predicate> predicates) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Accs_Auth_User> root = countQuery.from(Accs_Auth_User.class);

        countQuery.select(cb.count(root))
                .where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
