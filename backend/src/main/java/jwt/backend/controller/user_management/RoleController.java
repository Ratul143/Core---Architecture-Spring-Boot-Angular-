package jwt.backend.controller.user_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.user_management.RoleDto;
import jwt.backend.entity.user_management.Role;
import jwt.backend.service.user_management.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.PageResult;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.ROLE)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(roleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> roleList(@RequestParam("search") String search, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        PageResult<Role> roleList = roleService.roleList(search.toLowerCase(), page, size);
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> updateRole(@RequestParam("uniqueKey") String uniqueKey, @RequestBody RoleDto roleDto) {
        roleService.updateRole(uniqueKey, roleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> deleteRole(@RequestParam("uniqueKey") String uniqueKey) {
        roleService.deleteRole(uniqueKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(roleService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ROLE_BY_ID)
    public ResponseEntity<?> findRoleById(@RequestParam("id") Long id) {
        return new ResponseEntity<>(roleService.findRoleById(id), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<Role> roleList = roleService.findAll();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }
}
