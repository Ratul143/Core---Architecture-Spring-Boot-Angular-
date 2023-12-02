package jwt.backend.service.module_management;


import jwt.backend.dto.module_management.ModuleComponentsDto;
import jwt.backend.dto.module_management.ModuleDto;
import jwt.backend.entity.module_management.Module;
import jwt.backend.exception.ExceptionHandlerUtil;
import util.PageResult;

import java.util.List;
import java.util.Set;

public interface ModuleService {

    void create(ModuleDto moduleDto);

    PageResult<Module> list(String search, Integer page, Integer size);

    void update(String uniqueKey, ModuleDto moduleDto);

    Module findModuleById(Long id);

    Module findModuleByUniqueKey(String uniqueKey);

    void delete(String uniqueKey);

    Long count();

    List<Module> findAll();

    List<ModuleComponentsDto> findModuleComponents();

    Set<Module> findAllByKeyword(String keyword) throws ExceptionHandlerUtil;

    List<Module> findAllLikeUniqueKey(String uniqueKey);
}
