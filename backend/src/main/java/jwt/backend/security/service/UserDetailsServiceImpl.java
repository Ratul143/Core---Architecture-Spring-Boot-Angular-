package jwt.backend.security.service;

import jwt.backend.constant.CustomMessage;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import jwt.backend.repository.user_management.AccsAuthUserRepository;
import jwt.backend.security.entity.UserPrincipalV2;
import jwt.backend.service.user_management.LoginAttemptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccsAuthUserRepository accsAuthUserRepository;

    @Autowired
    LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Accs_Auth_User accs_auth_user = accsAuthUserRepository.findUserByUsername(username);
        if (accs_auth_user == null) {
            log.error(CustomMessage.NO_USER_FOUND + username);
            throw new UsernameNotFoundException(CustomMessage.NO_USER_FOUND + username);
        } else {
            validateLoginAttemptV2(accs_auth_user);
            accsAuthUserRepository.save(accs_auth_user);
            UserPrincipalV2 userPrincipalV2 = new UserPrincipalV2(accs_auth_user);
            log.info("Returning found username: " + username);
            return userPrincipalV2;
        }
    }

    private void validateLoginAttemptV2(Accs_Auth_User accs_auth_user) {
        if (accs_auth_user.isAccountLocked()) {
            if (loginAttemptService.hasExceededMaxAttempt(accs_auth_user.getUsername())) {
                // The user has exceeded the maximum login attempts, keep the account locked.
                accs_auth_user.setAccountLocked(true);
            } else {
                // The user has not exceeded the maximum login attempts, unlock the account.
                accs_auth_user.setAccountLocked(false);
                loginAttemptService.evictUserFromLoginAttemptCache(accs_auth_user.getUsername());
            }
        } else {
            // The account is not locked, clear the user from the login attempt cache.
            loginAttemptService.evictUserFromLoginAttemptCache(accs_auth_user.getUsername());
        }
    }

}
