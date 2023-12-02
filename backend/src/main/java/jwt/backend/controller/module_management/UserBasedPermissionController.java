package jwt.backend.controller.module_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.module_management.UserBasedPermissionDto;
import jwt.backend.service.module_management.UserBasedPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.USER_BASED_PERMISSION)
@RequiredArgsConstructor
public class UserBasedPermissionController {

    private final UserBasedPermissionService userBasedPermissionService;

    @PostMapping(ApiUrl.CREATE_OR_UPDATE)
    public ResponseEntity<?> createOrUpdate(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId, @RequestBody List<UserBasedPermissionDto> userBasedPermissionDto) {
        userBasedPermissionService.createOrUpdate(userId, roleId, userBasedPermissionDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_USER_AND_ROLE_ID)
    public ResponseEntity<?> findAllByUserAndRoleId(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        return new ResponseEntity<>(userBasedPermissionService.findAllByUserAndRoleId(userId, roleId), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.REVERT_TO_DEFAULT_ROLE)
    public ResponseEntity<?> revertToDefaultRole(@RequestParam("userId") Long userId) {
        userBasedPermissionService.revertToDefaultRole(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
