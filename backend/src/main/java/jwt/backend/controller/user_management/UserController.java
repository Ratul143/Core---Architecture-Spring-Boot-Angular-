package jwt.backend.controller.user_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.constant.StatusCode;
import jwt.backend.dto.ChangePasswordDto;
import jwt.backend.dto.request.auth.LoginRequest;
import jwt.backend.dto.request.auth.RegistrationRequest;
import jwt.backend.dto.request.auth.UserCreateRequest;
import jwt.backend.dto.request.auth.UserUpdateRequest;
import jwt.backend.dto.response.CommonResponse;
import jwt.backend.dto.response.LoginResponse;
import jwt.backend.dto.user_management.AccsUserListDto;
import jwt.backend.dto.user_management.EmployeeTypeDto;
import jwt.backend.exception.ExceptionHandling;
import jwt.backend.exception.user_management.EmailNotFoundException;
import jwt.backend.security.entity.HttpResponse;
import jwt.backend.security.entity.UserPrincipalV2;
import jwt.backend.security.utility.JWTTokenProvider;
import jwt.backend.service.user_management.AuthService;
import jwt.backend.service.user_management.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static jwt.backend.constant.AppConstant.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(ApiUrl.BASE_API + ApiUrl.USER)
public class UserController extends ExceptionHandling {

    private final UserService userService;
    private final AuthService authService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping(ApiUrl.SIGN_IN)
    public ResponseEntity<LoginResponse> signIn(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest) {
        LoginResponse response = userService.signIn(loginRequest, httpServletRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping(ApiUrl.REGISTRATION)
    public ResponseEntity<CommonResponse> registration(@RequestBody RegistrationRequest request) {
        CommonResponse response = userService.registration(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<CommonResponse> addUser(@RequestBody UserCreateRequest request) {
            CommonResponse response = userService.addUser(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(ApiUrl.UPDATE_USER_INFO)
    public ResponseEntity<CommonResponse> updateUserInfo(@RequestBody UserUpdateRequest request) {
        CommonResponse response = userService.updateUserInfo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(ApiUrl.DELETE_BY_ID)
    public ResponseEntity<CommonResponse> deleteUserProfile(@PathVariable("id") Long id) {
        CommonResponse response = userService.deleteUserProfile(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(ApiUrl.RESET_USER_PASSWORD)
    public ResponseEntity<CommonResponse> resetPassword(@RequestParam("email") String email) {
        try {
            CommonResponse response = userService.resetPassword(email);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (EmailNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse(StatusCode.NOT_FOUND, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonResponse(StatusCode.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PutMapping(ApiUrl.CHANGE_USER_PASSWORD)
    public ResponseEntity<CommonResponse> changeUserPassword(@RequestBody ChangePasswordDto changePasswordDto) {
        CommonResponse response = userService.changeUserPassword(changePasswordDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = ApiUrl.GET_PROFILE_IMAGE, produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(getUserDirectory() + username + FORWARD_SLASH + fileName));
    }

    // Robohash image processing
    @GetMapping(path = ApiUrl.GET_TEMP_PROFILE_IMAGE, produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while ((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(userService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL + ApiUrl.AUTH_USERS)
    public ResponseEntity<Page<AccsUserListDto>> getAuthUsers(
            @RequestParam(required = false) String searchValueForUsername,
            @RequestParam(required = false) String searchValueForEmail,
            @RequestParam(required = false) String searchValueForUserType,
            @RequestParam(required = false) String searchValueForCellNo,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        try {
            page = page < 0 ? 0 : page - 1;
            size = size <= 0 ? 10 : size;
            Page<AccsUserListDto> userPage = authService.authUserList(
                    searchValueForUsername,
                    searchValueForEmail,
                    searchValueForUserType,
                    searchValueForCellNo,
                    page,
                    size);
            return ResponseEntity.ok(userPage);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error("An error occurred while retrieving users", e);

            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = HttpResponse
                .builder()
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .reason(httpStatus.getReasonPhrase().toUpperCase())
                .message(message.toUpperCase())
                .build();
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    private HttpHeaders getJwtHeaderV2(UserPrincipalV2 user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtTokenV2(user));
        return headers;
    }

}
