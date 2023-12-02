package jwt.backend.service.module_management;


import jwt.backend.dto.module_management.UserBasedPermissionDto;
import jwt.backend.entity.module_management.UserBasedPermission;

import java.util.List;
import java.util.Set;

public interface UserBasedPermissionService {
    void createOrUpdate(Long userId, Long roleId, List<UserBasedPermissionDto> userBasedPermissionDtos);

    Set<UserBasedPermission> findAllByUserAndRoleId(Long userId, Long roleId);

    void revertToDefaultRole(Long userId);
}
