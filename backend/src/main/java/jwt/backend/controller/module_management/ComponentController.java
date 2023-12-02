package jwt.backend.controller.module_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.module_management.ComponentDto;
import jwt.backend.entity.module_management.Component;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.service.module_management.ComponentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import util.PageResult;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.COMPONENT)
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody ComponentDto componentDto) {
        componentService.create(componentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> ComponentList(@RequestParam("search") String search, @RequestParam("searchByPath") String searchByPath, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        PageResult<Component> componentList = componentService.list(search.toLowerCase(), searchByPath.toLowerCase(), page, size);
        return new ResponseEntity<>(componentList, HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("uniqueKey") String uniqueKey, @RequestBody ComponentDto ComponentDto) {
        componentService.update(uniqueKey, ComponentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("uniqueKey") String uniqueKey) {
        componentService.delete(uniqueKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(componentService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<Component> ComponentList = componentService.findAll();
        return new ResponseEntity<>(ComponentList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_MODULE_ID)
    public ResponseEntity<?> findAllComponentsByModuleId(@RequestParam("moduleId") Long moduleId) {
        List<Component> Components = componentService.findAllByModuleId(moduleId);
        return new ResponseEntity<>(Components, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_ORPHAN_COMPONENTS_BY_MODULE_UNIQUE_KEY)
    public ResponseEntity<?> findAllOrphanComponentsByModuleUniqueKey(@RequestParam("moduleUniqueKey") String moduleUniqueKey, @RequestParam("keyword") String keyword) throws ExceptionHandlerUtil {
        Set<Component> components = componentService.findAllOrphanComponentsByModuleUniqueKey(moduleUniqueKey, keyword);
        return new ResponseEntity<>(components, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_SUB_MODULE_ID)
    public ResponseEntity<?> findAllBySubModuleId(@RequestParam("subModuleId") Long subModuleId) {
        return new ResponseEntity<>(componentService.findAllBySubModuleId(subModuleId), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_BY_PATH)
    public ResponseEntity<?> findByPath(@RequestParam("path") String path) {
        return new ResponseEntity<>(componentService.findByPath(path), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_UNIQUE_KEY_AND_KEYWORD)
    public ResponseEntity<?> findAllByKeyword(@RequestParam("parentUniqueKey") String parentUniqueKey, @RequestParam("keyword") String keyword) {
        try{
            return new ResponseEntity<>(componentService.findAllByKeyword(parentUniqueKey, keyword), HttpStatus.OK);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @GetMapping(ApiUrl.FIND_ALL_COMPONENT_BY_SUB_MODULE_UNIQUE_KEY_AND_KEYWORD)
    public ResponseEntity<?> findAllComponentBySubModuleUniqueKeyword(@RequestParam("parentUniqueKey") String parentUniqueKey, @RequestParam("keyword") String keyword) {
        try {
            return new ResponseEntity<>(componentService.findAllComponentBySubModuleUniqueKeyAndKeyword(parentUniqueKey, keyword), HttpStatus.OK);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @GetMapping(ApiUrl.FIND_ALL_LIKE_UNIQUE_KEY)
    public ResponseEntity<?> findAllLikeUniqueKey(@RequestParam("uniqueKey") String uniqueKey) {
        return new ResponseEntity<>(componentService.findAllLikeUniqueKey(uniqueKey.toLowerCase()), HttpStatus.OK);
    }
}
