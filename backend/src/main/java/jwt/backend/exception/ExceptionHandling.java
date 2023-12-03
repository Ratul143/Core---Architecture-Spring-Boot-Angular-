package jwt.backend.exception;

import jwt.backend.exception.CustomExceptionHandler.DataExistsException;
import jwt.backend.exception.approval.FormNotFoundException;
import jwt.backend.exception.approval.StatusNotFoundException;
import jwt.backend.exception.user_management.*;
import jwt.backend.security.entity.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.NoResultException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionHandling {
    public static final String ACCOUNT_LOCKED = "Your account has been locked! Please contact to administration.";
    public static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request!";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "An error occurred while processing the request";
    public static final String INCORRECT_CREDENTIALS = "You have entered incorrect Username Or Password!";
    public static final String ACCOUNT_DISABLED = "Your account has been disabled!";
    public static final String ERROR_PROCESSING_FILE = "Error occurred when processing the file";
    public static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission!";
    public static final String NO_TYPE_OF_RM_FOUND = "No Type Of R/M Found!";
    public static final String PRODUCT_DETAILS_EXISTS = "Product Details Exists!";
    public static final String INSUFFICIENT_BALANCE = "Insufficient Balance!";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = HttpResponse
                .builder()
                .httpStatusCode(httpStatus.value())
                .httpStatus(httpStatus)
                .reason(httpStatus.getReasonPhrase().toUpperCase())
                .message(message.toUpperCase())
                .build();
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisabledException() {
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialException() {
        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException() {
        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> lockedException() {
        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExistsException(EmailExistException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage().toUpperCase());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage().toUpperCase());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage().toUpperCase());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<HttpResponse> noFoundException(NoResultException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpResponse> iOException(IOException exception) {
        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<HttpResponse> usernameExistsException(UsernameExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage().toUpperCase());
    }

    @ExceptionHandler(InvalidTypeException.class)
    public ResponseEntity<HttpResponse> notAnImageFileException(UsernameExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage().toUpperCase());
    }


    @ExceptionHandler(ZeroValueException.class)
    public ResponseEntity<HttpResponse> zeroValueException(ZeroValueException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NullValueException.class)
    public ResponseEntity<HttpResponse> nullValueException(NullValueException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<HttpResponse> statusNotFoundException(StatusNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(FormNotFoundException.class)
    public ResponseEntity<HttpResponse> formNotFoundException(FormNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicateFoundException.class)
    public ResponseEntity<HttpResponse> duplicateFoundException(DuplicateFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(KeyNotFoundException.class)
    public ResponseEntity<HttpResponse> keyNotFoundException(KeyNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(ModuleExistsException.class)
    public ResponseEntity<HttpResponse> moduleExistsException(ModuleExistsException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ComponentsExistsException.class)
    public ResponseEntity<HttpResponse> componentExistsException(PermissionNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ModuleNotFoundException.class)
    public ResponseEntity<HttpResponse> moduleNotFoundException(ModuleNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<HttpResponse> passwordNotMatchedException(PasswordNotMatchException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<Object> handleDataExistsException(DataExistsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("uniqueKey", ex.getUniqueKey());
        body.put("year", ex.getYear());
        body.put("week", ex.getWeek());
        body.put("unit", ex.getUnit());
        body.put("message", ex.getMessage());
        body.put("responseCode", ex.getResponseCode());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

}

