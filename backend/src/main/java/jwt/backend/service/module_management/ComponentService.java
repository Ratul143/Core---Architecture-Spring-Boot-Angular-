package jwt.backend.service.module_management;


import jwt.backend.dto.module_management.ComponentDto;
import jwt.backend.entity.module_management.Component;
import jwt.backend.exception.ExceptionHandlerUtil;
import util.PageResult;

import java.util.List;
import java.util.Set;

public interface ComponentService {

    PageResult<Component> list(String search, String searchByPath, Integer page, Integer size);

    void create(ComponentDto componentDto);

    void update(String uniqueKey, ComponentDto componentDto);

    void delete(String uniqueKey);

    Long count();

    List<Component> findAll();

    List<Component> findAllByModuleId(Long moduleId);

    Component findByPath(String path);

    Set<Component> findAllOrphanComponentsByModuleUniqueKey(String moduleUniqueKey, String keyword) throws ExceptionHandlerUtil;

    Set<Component> findAllBySubModuleId(Long subModuleId);

    Set<Component> findAllByKeyword(String parentUniqueKey, String keyword) throws ExceptionHandlerUtil;

    Set<Component> findAllComponentBySubModuleUniqueKeyAndKeyword(String parentUniqueKey, String keyword) throws ExceptionHandlerUtil;

    List<Component> findAllLikeUniqueKey(String uniqueKey);
}
