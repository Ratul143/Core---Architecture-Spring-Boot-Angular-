package jwt.backend.controller.module_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.module_management.RoleBasedPermissionDto;
import jwt.backend.entity.module_management.RoleBasedPermission;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.service.module_management.RoleBasedPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import util.PageResult;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.ROLE_BASED_PERMISSION)
@RequiredArgsConstructor
public class RoleBasedPermissionController {

    private final RoleBasedPermissionService roleBasedPermissionService;

    @PostMapping(ApiUrl.CREATE_OR_UPDATE)
    public ResponseEntity<?> createOrUpdate(@RequestParam("roleUniqueKey") String roleUniqueKey, @RequestBody List<RoleBasedPermissionDto> roleBasedPermissionDto) throws ExceptionHandlerUtil {
        roleBasedPermissionService.createOrUpdate(roleUniqueKey, roleBasedPermissionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<RoleBasedPermission> roleBasePermissionList = roleBasedPermissionService.findAll();
        return new ResponseEntity<>(roleBasePermissionList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_SIDENAV_ELEMENTS)
    public ResponseEntity<?> findAllSideNavElements() {
        return new ResponseEntity<>(roleBasedPermissionService.findAllSideNavElements(), HttpStatus.OK);
    }

//    @GetMapping(ApiUrl.FIND_COMPONENT_PERMISSION)
//    public ResponseEntity<?> findComponentPermission(@RequestParam("componentId") Long componentId) {
//        return new ResponseEntity<>(roleBasedPermissionService.findComponentPermission(componentId), HttpStatus.OK);
//    }

    @GetMapping(ApiUrl.FIND_ALL_BY_ROLE_ID)
    public ResponseEntity<?> list(@RequestParam("roleUniqueKey") String roleUniqueKey, @RequestParam("searchByModule") String searchByModule, @RequestParam("searchBySubModule") String searchBySubModule, @RequestParam("searchByComponent") String searchByComponent, @RequestParam("searchByAny") String searchByAny, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        try {
            page = page < 0 ? 0 : page - 1;
            size = size <= 0 ? 10 : size;
            PageResult<RoleBasedPermission> roleBasedPermissionList = roleBasedPermissionService.findAllByUniqueKey(roleUniqueKey, searchByModule, searchBySubModule, searchByComponent, searchByAny, page, size);
            return new ResponseEntity<>(roleBasedPermissionList, HttpStatus.OK);
        } catch (ExceptionHandlerUtil exception) {
            throw new ResponseStatusException(exception.code, exception.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }
}
