package jwt.backend.service.module_management;


import jwt.backend.dto.module_management.SubModuleDto;
import jwt.backend.entity.module_management.SubModule;
import jwt.backend.exception.ExceptionHandlerUtil;
import util.PageResult;

import java.util.List;
import java.util.Set;

public interface SubModuleService {

    void create(SubModuleDto subModuleDto);

    PageResult<SubModule> list(String search, Integer page, Integer size);

    void update(String uniqueKey, SubModuleDto subModuleDto);

    SubModule findSubModuleById(Long id);

    SubModule findSubModuleByUniqueKey(String uniqueKey);

    void delete(String uniqueKey);

    Long count();

    List<SubModule> findAll();

    Set<SubModule> findAllSubModulesByModuleId(Long moduleId);

    Set<SubModule> findAllByKeyword(String parentUniqueKey, String keyword) throws ExceptionHandlerUtil;

    List<SubModule> findAllLikeUniqueKey(String uniqueKey);
}
