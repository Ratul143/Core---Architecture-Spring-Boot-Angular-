package jwt.backend.service.module_management;


import jwt.backend.dto.module_management.UserBasedPermissionDto;
import jwt.backend.entity.module_management.RoleBasedPermission;
import jwt.backend.entity.module_management.UserBasedPermission;
import jwt.backend.repository.module_management.*;
import jwt.backend.repository.user_management.AccsAuthUserRepository;
import jwt.backend.repository.user_management.RoleRepository;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class UserBasedPermissionServiceImpl implements UserBasedPermissionService {

    private final RoleBasedPermissionRepository roleBasedPermissionRepository;
    private final UserBasedPermissionRepository userBasedPermissionRepository;
    private final ComponentRepository componentRepository;
    private final ModuleRepository moduleRepository;
    private final SubModuleRepository subModuleRepository;
    private final AuthService authService;
    private final CommonService commonService;
    private final RoleRepository roleRepository;
    private final AccsAuthUserRepository accsAuthUserRepository;

    @Override
    public void createOrUpdate(Long userId, Long roleId, List<UserBasedPermissionDto> incomingPermissions) {
        List<RoleBasedPermission> existingRoleBasedPermissions = roleBasedPermissionRepository.findAllByRoleIdAndDeletedAtIsNull(roleId);
        List<UserBasedPermissionDto> matchedPermissions = incomingPermissions.stream()
                .filter(incomingPermission -> incomingPermission.getCreate() ||
                        incomingPermission.getRead() ||
                        incomingPermission.getUpdate() ||
                        incomingPermission.getDelete())
                .filter(incomingPermission -> existingRoleBasedPermissions.stream()
                        .anyMatch(existingPermission -> existingPermission.getComponent().getId().equals(incomingPermission.getComponentId())
                                && existingPermission.getCreate().equals(incomingPermission.getCreate())
                                && existingPermission.getRead().equals(incomingPermission.getRead())
                                && existingPermission.getUpdate().equals(incomingPermission.getUpdate())
                                && existingPermission.getDelete().equals(incomingPermission.getDelete())))
                .toList();
        matchedPermissions.forEach(match -> deletePermission(userId, match.getComponentId()));

        incomingPermissions.stream()
                .filter(incomingPermission -> incomingPermission.getCreate() ||
                        incomingPermission.getRead() ||
                        incomingPermission.getUpdate() ||
                        incomingPermission.getDelete())
                .filter(incomingPermission -> existingRoleBasedPermissions.stream()
                        .noneMatch(existingPermission -> existingPermission.getComponent().getId().equals(incomingPermission.getComponentId())
                                && existingPermission.getCreate().equals(incomingPermission.getCreate())
                                && existingPermission.getRead().equals(incomingPermission.getRead())
                                && existingPermission.getUpdate().equals(incomingPermission.getUpdate())
                                && existingPermission.getDelete().equals(incomingPermission.getDelete())))
                .forEach(nonMatchPermission -> {
                    UserBasedPermission createOrUpdate = userBasedPermissionRepository.findByUserIdAndComponentIdAndDeletedAtNull(userId, nonMatchPermission.getComponentId());
                    if (createOrUpdate == null) {
                        createOrUpdate = new UserBasedPermission();
                    }
                    createOrUpdate.setUniqueKey(commonService.randomAlphaNumeric());
                    createOrUpdate.setRole(roleRepository.findByIdAndDeletedAtNull(roleId));
                    createOrUpdate.setModule(nonMatchPermission.getModuleId() != null ? moduleRepository.findByIdAndDeletedAtNull(nonMatchPermission.getModuleId()) : null);
                    createOrUpdate.setSubModule(nonMatchPermission.getSubModuleId() != null ? subModuleRepository.findByIdAndDeletedAtNull(nonMatchPermission.getSubModuleId()) : null);
                    createOrUpdate.setComponent(nonMatchPermission.getComponentId() != null ? componentRepository.findByIdAndDeletedAtNullAndIsActiveTrue(nonMatchPermission.getComponentId()) : null);
                    createOrUpdate.setUser(accsAuthUserRepository.findByIdAndDeletedAtNull(userId)); // newly added
                    createOrUpdate.setCreate(nonMatchPermission.getCreate());
                    createOrUpdate.setRead(nonMatchPermission.getRead());
                    createOrUpdate.setUpdate(nonMatchPermission.getUpdate());
                    createOrUpdate.setDelete(nonMatchPermission.getDelete());
                    createOrUpdate.setCreatedAt(commonService.getCurrentDateTime());
                    createOrUpdate.setCreatedBy(authService.getAuthUserId());
                    userBasedPermissionRepository.save(createOrUpdate);
                });
    }

    @Override
    public Set<UserBasedPermission> findAllByUserAndRoleId(Long userId, Long roleId) {
        Set<UserBasedPermission> combinedPermissionList = new HashSet<>();
        List<UserBasedPermission> userBasedPermissionsList = userBasedPermissionRepository.findAllByUserIdAndDeletedAtIsNull(userId);
        for (UserBasedPermission permission : userBasedPermissionsList) {
            if (permission != null) {
                combinedPermissionList.add(permission);
            }
        }

        // create a mapping function that maps a RoleBasedPermission object to a UserBasedPermission object
        Function<RoleBasedPermission, UserBasedPermission> mapRoleToUserPermission = rolePermission -> {
            UserBasedPermission userPermission = new UserBasedPermission();
            userPermission.setModule(rolePermission.getModule());
            userPermission.setSubModule(rolePermission.getSubModule());
            userPermission.setComponent(rolePermission.getComponent());
            userPermission.setCreate(rolePermission.getCreate());
            userPermission.setRead(rolePermission.getRead());
            userPermission.setUpdate(rolePermission.getUpdate());
            userPermission.setDelete(rolePermission.getDelete());
            return userPermission;
        };

        List<RoleBasedPermission> roleBasedExistingPermissions = roleBasedPermissionRepository.findAllByRoleIdAndDeletedAtIsNull(roleId);
        for (RoleBasedPermission rolePermission : roleBasedExistingPermissions) {
            UserBasedPermission userPermission = mapRoleToUserPermission.apply(rolePermission);
            if (combinedPermissionList.stream().noneMatch(p -> p.getComponent().getId().equals(userPermission.getComponent().getId()))) {
                combinedPermissionList.add(userPermission);
            }
        }
        return combinedPermissionList;
    }

    private void deletePermission(Long userId, Long componentId) {
        UserBasedPermission findUserBasedPermission = userBasedPermissionRepository.findByUserIdAndComponentIdAndDeletedAtNull(userId, componentId);
        if (findUserBasedPermission != null) {
            userBasedPermissionRepository.delete(findUserBasedPermission);
        }
    }

    @Override
    public void revertToDefaultRole(Long userId) {
        Set<UserBasedPermission> findAllUserBasedPermission = userBasedPermissionRepository.findAllByUserIdAndDeletedAtNull(userId);
        if (findAllUserBasedPermission != null) {
            userBasedPermissionRepository.deleteAll(findAllUserBasedPermission);
        }
    }
}
