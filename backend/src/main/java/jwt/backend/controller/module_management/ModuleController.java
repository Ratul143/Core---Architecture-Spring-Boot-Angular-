package jwt.backend.controller.module_management;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.module_management.ModuleComponentsDto;
import jwt.backend.dto.module_management.ModuleDto;
import jwt.backend.entity.module_management.Module;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.service.module_management.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import util.PageResult;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.MODULE)
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody ModuleDto ModuleDto) {
        moduleService.create(ModuleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> ModuleList(@RequestParam("search") String search, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        PageResult<Module> moduleList = moduleService.list(search.toLowerCase(), page, size);
        return new ResponseEntity<>(moduleList, HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> updateModule(@RequestParam("uniqueKey") String uniqueKey, @RequestBody ModuleDto ModuleDto) {
        moduleService.update(uniqueKey, ModuleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> deleteModule(@RequestParam("uniqueKey") String uniqueKey) {
        moduleService.delete(uniqueKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(moduleService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<Module> ModuleList = moduleService.findAll();
        return new ResponseEntity<>(ModuleList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_MODULE_BY_UNIQUE_KEY)
    public ResponseEntity<?> findModuleByUniqueKey(@RequestParam("uniqueKey") String uniqueKey) {
        Module Module = moduleService.findModuleByUniqueKey(uniqueKey);
        return new ResponseEntity<>(Module, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_MODULE_COMPONENTS)
    public ResponseEntity<?> findModuleComponents() {
        List<ModuleComponentsDto> moduleComponents = moduleService.findModuleComponents();
        return new ResponseEntity<>(moduleComponents, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_ALL_BY_KEYWORD)
    public ResponseEntity<?> findAllByKeyword(@RequestParam("keyword") String keyword) {
        try {
            return new ResponseEntity<>(moduleService.findAllByKeyword(keyword), HttpStatus.OK);
        } catch (ExceptionHandlerUtil exception) {
            throw new ResponseStatusException(exception.code, exception.getMessage());
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @GetMapping(ApiUrl.FIND_ALL_LIKE_UNIQUE_KEY)
    public ResponseEntity<?> findAllLikeUniqueKey(@RequestParam("uniqueKey") String uniqueKey) {
        return new ResponseEntity<>(moduleService.findAllLikeUniqueKey(uniqueKey.toLowerCase()), HttpStatus.OK);
    }
}
