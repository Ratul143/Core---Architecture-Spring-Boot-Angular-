package jwt.backend.security.listener;

import jwt.backend.service.user_management.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @author Jaber
 * @Date 8/24/2022
 * @Project backend
 */
@Component
@AllArgsConstructor
public class AuthenticationFailureListener {
    private final LoginAttemptService loginAttemptService;

    @EventListener
    public void authenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(username);
        }
    }
}
