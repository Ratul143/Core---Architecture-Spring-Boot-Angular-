package jwt.backend.repository.module_management;

import jwt.backend.entity.module_management.UserBasedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserBasedPermissionRepository extends JpaRepository<UserBasedPermission, Long> {
    List<UserBasedPermission> findAllByDeletedAtNull();

    List<UserBasedPermission> findAllByModuleIdAndDeletedAtNull(Long id);

    UserBasedPermission findByUniqueKeyAndDeletedAtNull(String uniqueKey);

    UserBasedPermission findByUserIdAndComponentIdAndDeletedAtNull(Long userId, Long componentId);

    UserBasedPermission findByRoleIdAndComponentUniqueKeyAndDeletedAtNull(Long roleId, String componentUniqueKey);

    Set<UserBasedPermission> findAllByUserIdAndDeletedAtNull(Long userId);

    Long countAllByDeletedAtIsNull();

    List<UserBasedPermission> findAllByUserIdAndDeletedAtIsNull(Long userId);
}