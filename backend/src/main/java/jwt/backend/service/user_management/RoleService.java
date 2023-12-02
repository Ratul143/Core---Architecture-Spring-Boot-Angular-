package jwt.backend.service.user_management;


import jwt.backend.dto.user_management.RoleDto;
import jwt.backend.entity.user_management.Role;
import util.PageResult;

import java.util.List;

public interface RoleService {

    void createRole(RoleDto roleDto);

    PageResult<Role> roleList(String search, Integer page, Integer size);

    void updateRole(String uniqueKey, RoleDto roleDto);

    Role findRoleById(Long id);

    Role findRoleByUniqueKey(String uniqueKey);

    void deleteRole(String uniqueKey);

    Long count();

    List<Role> findAll();

    Role findByRole(String role);

}
