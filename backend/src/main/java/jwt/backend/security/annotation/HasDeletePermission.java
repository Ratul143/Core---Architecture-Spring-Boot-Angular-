package jwt.backend.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@roleBasedPermissionServiceImpl.hasDeleteAuthority() == true")
public @interface HasDeletePermission {
}
