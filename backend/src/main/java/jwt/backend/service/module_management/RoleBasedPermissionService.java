package jwt.backend.service.module_management;


import jwt.backend.dto.MenuContainer;
import jwt.backend.dto.module_management.RoleBasedPermissionDto;
import jwt.backend.entity.module_management.RoleBasedPermission;
import jwt.backend.exception.ExceptionHandlerUtil;
import util.PageResult;

import java.util.List;

public interface RoleBasedPermissionService {
    void createOrUpdate(String roleUniqueKey, List<RoleBasedPermissionDto> moduleForRoleDto) throws ExceptionHandlerUtil;

    List<RoleBasedPermission> findAll();

    MenuContainer findAllSideNavElements();

    PageResult<RoleBasedPermission> findAllByUniqueKey(String roleUniqueKey, String searchByModule, String searchBySubModule, String searchByComponent, String searchByAny, Integer page, Integer size) throws ExceptionHandlerUtil;
}
