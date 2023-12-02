package jwt.backend.controller.module_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.module_management.SubModuleDto;
import jwt.backend.entity.module_management.SubModule;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.service.module_management.SubModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.SUB_MODULE)
@RequiredArgsConstructor
public class SubModuleController {

    private final SubModuleService subModuleService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody SubModuleDto SubModuleDto) {
        subModuleService.create(SubModuleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> list(@RequestParam("search") String search, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        return new ResponseEntity<>(subModuleService.list(search.toLowerCase(), page, size), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("uniqueKey") String uniqueKey, @RequestBody SubModuleDto SubModuleDto) {
        subModuleService.update(uniqueKey, SubModuleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("uniqueKey") String uniqueKey) {
        subModuleService.delete(uniqueKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(subModuleService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<SubModule> SubModuleList = subModuleService.findAll();
        return new ResponseEntity<>(SubModuleList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_SUB_MODULE_BY_UNIQUE_KEY)
    public ResponseEntity<?> findSubModuleByUniqueKey(@RequestParam("uniqueKey") String uniqueKey) {
        return new ResponseEntity<>(subModuleService.findSubModuleByUniqueKey(uniqueKey), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_SUB_MODULES_BY_MODULE_ID)
    public ResponseEntity<?> findAllSubModulesByModuleId(@RequestParam("moduleId") Long moduleId) {
        return new ResponseEntity<>(subModuleService.findAllSubModulesByModuleId(moduleId), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_KEYWORD)
    public ResponseEntity<?> findAllByKeyword(@RequestParam("parentUniqueKey") String parentUniqueKey, @RequestParam("keyword") String keyword) {
        try {
            return new ResponseEntity<>(subModuleService.findAllByKeyword(parentUniqueKey, keyword), HttpStatus.OK);
        } catch (ExceptionHandlerUtil exception) {
            throw new ResponseStatusException(exception.code, exception.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_UNIQUE_KEY_AND_KEYWORD)
    public ResponseEntity<?> findAllSubModuleByUniqueKeyAndKeyword(@RequestParam("parentUniqueKey") String parentUniqueKey, @RequestParam("keyword") String keyword) {
        try {
            return new ResponseEntity<>(subModuleService.findAllByKeyword(parentUniqueKey, keyword), HttpStatus.OK);
        } catch (ExceptionHandlerUtil exception) {
            throw new ResponseStatusException(exception.code, exception.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @GetMapping(ApiUrl.FIND_ALL_LIKE_UNIQUE_KEY)
    public ResponseEntity<?> findAllLikeUniqueKey(@RequestParam("uniqueKey") String uniqueKey) {
        return new ResponseEntity<>(subModuleService.findAllLikeUniqueKey(uniqueKey.toLowerCase()), HttpStatus.OK);
    }
}
