package jwt.backend.service.module_management;

import jwt.backend.dto.module_management.ModuleComponentsDto;
import jwt.backend.dto.module_management.ModuleDto;
import jwt.backend.entity.module_management.Component;
import jwt.backend.entity.module_management.Module;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.repository.module_management.ComponentRepository;
import jwt.backend.repository.module_management.ModuleRepository;
import jwt.backend.repository.module_management.SubModuleRepository;
import jwt.backend.repository.module_management.UserBasedPermissionRepository;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.PageResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jwt.backend.service.CommonServiceImpl.nullCheck;

@Service
@Transactional
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final SubModuleRepository subModuleRepository;
    private final ComponentRepository componentRepository;
    private final UserBasedPermissionRepository componentPermissionRepository;
    private final AuthService authService;
    private final CommonService commonService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResult<Module> list(String search, Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Module> criteriaQuery = criteriaBuilder.createQuery(Module.class);
        Root<Module> root = criteriaQuery.from(Module.class);
        Predicate predicate = criteriaBuilder.conjunction();

        if (search != null && !search.isEmpty()) {
            Predicate modulePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("moduleName")), "%" + search.toLowerCase() + "%");
            Predicate iconPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("icon")), "%" + search.toLowerCase() + "%");
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.or(modulePredicate, iconPredicate)
            );
        }
        Predicate deletedAtIsNull = criteriaBuilder.isNull(root.get("deletedAt"));
        predicate = criteriaBuilder.and(predicate, deletedAtIsNull);
        criteriaQuery.where(predicate).orderBy(criteriaBuilder.asc(root.get("sortOrder")));
        TypedQuery<Module> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        return new PageResult<>(typedQuery.getResultList(), commonService.getCountWithPredicate(predicate, Module.class));
    }

    @Override
    public void create(ModuleDto moduleDto) {
        checkIfModuleExists(moduleDto.getModule());
        checkIfSortOrderExists(moduleDto.getSortOrder());
        Module createModule = new Module();
        createModule.setUniqueKey(commonService.randomAlphaNumeric());
        createModule.setModuleName(moduleDto.getModule());
        createModule.setIcon(moduleDto.getIcon());
        createModule.setIsActive(moduleDto.getIsActive());
        createModule.setSortOrder(moduleDto.getSortOrder());
        createModule.setCreatedAt(commonService.getCurrentDateTime());
        createModule.setCreatedBy(authService.getAuthUserId());
        moduleRepository.save(createModule);
    }

    @SneakyThrows
    public void checkIfModuleExists(String moduleName) {
        Module Module = moduleRepository.findByModuleNameAndDeletedAtNull(moduleName);
        if (Module != null) {
            throw new DuplicateFoundException("Module exists!");
        }
    }

    @SneakyThrows
    public void checkIfSortOrderExists(Long sortOrder) {
        Module Module = moduleRepository.findBySortOrderAndDeletedAtNull(sortOrder);
        if (Module != null) {
            throw new DuplicateFoundException("Sort order exists!");
        }
    }

    @Override
    public void update(String uniqueKey, ModuleDto moduleDto) {
        Module updateModule = findModuleByUniqueKey(uniqueKey);
        if (!Objects.equals(updateModule.getModuleName(), moduleDto.getModule())) {
            checkIfModuleExists(moduleDto.getModule());
        }

        if (!Objects.equals(updateModule.getSortOrder(), moduleDto.getSortOrder())) {
            checkIfSortOrderExists(moduleDto.getSortOrder());
        }
        updateModule.setModuleName(moduleDto.getModule());
        updateModule.setIcon(moduleDto.getIcon());
        updateModule.setIsActive(moduleDto.getIsActive());
        updateModule.setSortOrder(moduleDto.getSortOrder());
        updateModule.setUpdatedAt(commonService.getCurrentDateTime());
        updateModule.setUpdatedBy(authService.getAuthUserId());
        moduleRepository.save(updateModule);
    }

    @Override
    public Module findModuleById(Long id) {
        return moduleRepository.findByIdAndIsActiveTrueAndDeletedAtNull(id);
    }

    @Override
    public Module findModuleByUniqueKey(String uniqueKey) {
        return moduleRepository.findByUniqueKeyAndDeletedAtNull(uniqueKey);
    }

    @Override
    public void delete(String uniqueKey) {
        Module Module = findModuleByUniqueKey(uniqueKey);
        subModuleRepository.findAllByModuleIdAndDeletedAtNull(Module.getId()).forEach(subModule -> {
            subModule.setDeletedAt(commonService.getCurrentDateTime());
            subModule.setDeletedBy(authService.getAuthUserId());
            subModuleRepository.save(subModule);
        });

        componentRepository.findAllByModuleIdAndDeletedAtNull(Module.getId()).forEach(component -> {
            component.setDeletedAt(commonService.getCurrentDateTime());
            component.setDeletedBy(authService.getAuthUserId());
            componentRepository.save(component);
        });

        componentPermissionRepository.findAllByModuleIdAndDeletedAtNull(Module.getId()).forEach(componentPermission -> {
            componentPermission.setDeletedAt(commonService.getCurrentDateTime());
            componentPermission.setDeletedBy(authService.getAuthUserId());
            componentPermissionRepository.save(componentPermission);
        });

        Module.setDeletedAt(commonService.getCurrentDateTime());
        Module.setDeletedBy(authService.getAuthUserId());
        moduleRepository.save(Module);
    }

    @Override
    public Long count() {
        return moduleRepository.countAllByDeletedAtNull();
    }

    @Override
    public List<Module> findAll() {
        return moduleRepository.findAllByIsActiveTrueAndDeletedAtNullOrderBySortOrderAsc();
    }

    @Override
    public List<ModuleComponentsDto> findModuleComponents() {
        List<ModuleComponentsDto> moduleComponentsList = new ArrayList<>();
        Set<Module> modules = moduleRepository.findAllByDeletedAtNull();
        for (Module module : modules) {
            List<Component> componentSet = componentRepository.findAllByModuleIdAndDeletedAtNullAndIsActiveTrue(module.getId());
            ModuleComponentsDto moduleComponentsDto = new ModuleComponentsDto();
            moduleComponentsDto.setModule(module);
            moduleComponentsDto.setComponents(componentSet);
            moduleComponentsList.add(moduleComponentsDto);
        }
        return moduleComponentsList;
    }

    @Override
    public Set<Module> findAllByKeyword(String keyword) throws ExceptionHandlerUtil {
        try {
            if (nullCheck(keyword)) {
                return moduleRepository.findLastTenEntriesByKeyword(keyword, PageRequest.of(0, 10));
            } else {
                return moduleRepository.findAllByModuleNameAndDeletedAtNullOrderByIdDesc(PageRequest.of(0, 10));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public List<Module> findAllLikeUniqueKey(String uniqueKey) {
        return moduleRepository.findAllLikeUniqueKey(uniqueKey, PageRequest.of(0, 5));
    }
}
