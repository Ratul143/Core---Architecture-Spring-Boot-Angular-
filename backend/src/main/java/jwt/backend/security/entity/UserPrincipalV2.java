package jwt.backend.security.entity;

import jwt.backend.entity.user_management.Role;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class UserPrincipalV2 implements UserDetails {
    private final Accs_Auth_User accs_auth_user;

    public UserPrincipalV2(Accs_Auth_User accs_auth_user) {
        this.accs_auth_user = accs_auth_user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(this.accs_auth_user.getRole().getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Long getId() {
        return this.accs_auth_user.getId();
    }

    public String getFullName() {
        return this.accs_auth_user.getFullName();
    }

    public String getEmail() {
        return this.accs_auth_user.getEmail();
    }

    public Role getRole() {
        return this.accs_auth_user.getRole();
    }

    @Override
    public String getPassword() {
        return this.accs_auth_user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.accs_auth_user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.accs_auth_user.isEnabled();
    }
}
