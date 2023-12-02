package jwt.backend.repository.module_management;

import jwt.backend.entity.module_management.RoleBasedPermission;
import jwt.backend.entity.user_management.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleBasedPermissionRepository extends JpaRepository<RoleBasedPermission, Long> {
    List<RoleBasedPermission> findAllByDeletedAtNull();

    RoleBasedPermission findByRoleUniqueKeyAndComponentUniqueKeyAndDeletedAtNull(String roleUniqueKey, String componentUniqueKey);

    RoleBasedPermission findByRoleIdAndComponentIdAndDeletedAtNull(Long roleId, Long componentId);

    Set<RoleBasedPermission> findAllByRoleIdAndDeletedAtNull(Long roleId);

    RoleBasedPermission findByUniqueKeyAndDeletedAtNull(String uniqueKey);

    List<RoleBasedPermission> findAllByRoleIdAndDeletedAtIsNull(Long roleId);
}