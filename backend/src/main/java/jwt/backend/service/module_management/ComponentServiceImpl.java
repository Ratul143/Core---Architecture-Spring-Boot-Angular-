package jwt.backend.service.module_management;


import jwt.backend.dto.module_management.ComponentDto;
import jwt.backend.entity.module_management.Component;
import jwt.backend.entity.module_management.Module;
import jwt.backend.exception.CommonException;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.repository.module_management.ComponentRepository;
import jwt.backend.repository.module_management.ModuleRepository;
import jwt.backend.repository.module_management.SubModuleRepository;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jwt.backend.service.CommonServiceImpl.nullCheck;

@Service
@Transactional
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ModuleRepository moduleRepository;
    private final SubModuleRepository subModuleRepository;
    private final AuthService authService;
    private final CommonService commonService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResult<Component> list(String search, String searchByPath, Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Component> criteriaQuery = criteriaBuilder.createQuery(Component.class);
        Root<Component> root = criteriaQuery.from(Component.class);
        Predicate predicate = criteriaBuilder.conjunction();

        if (nullCheck(search)) {
            Predicate modulePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("module").get("moduleName")), "%" + search.toLowerCase() + "%");
            Predicate componentPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("componentName")), "%" + search.toLowerCase() + "%");
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.or(modulePredicate, componentPredicate)
            );
        }

        if (nullCheck(searchByPath)) {
            Predicate iconPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("icon")), "%" + searchByPath.toLowerCase() + "%");
            Predicate pathPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("path")), "%" + searchByPath.toLowerCase() + "%");
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.or(iconPredicate, pathPredicate)
            );
        }
        Predicate deletedAtIsNull = criteriaBuilder.isNull(root.get("deletedAt"));
        predicate = criteriaBuilder.and(predicate, deletedAtIsNull);
        criteriaQuery.where(predicate).orderBy(criteriaBuilder.asc(root.get("module")));
        TypedQuery<Component> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        return new PageResult<>(typedQuery.getResultList(), commonService.getCountWithPredicate(predicate, Component.class));
    }

    @Override
    public void create(ComponentDto componentDto) {
        checkIfModuleAndComponentExists(componentDto);
        checkIfPathExists(componentDto);
        checkIfSortOrderExists(componentDto);
        Component createComponent = new Component();
        createComponent.setUniqueKey(commonService.randomAlphaNumeric());
        createComponent.setModule(moduleRepository.findByIdAndDeletedAtNull(componentDto.getModule()));
        if (componentDto.getSubModule() != null) {
            createComponent.setSubModule(subModuleRepository.findByUniqueKeyAndDeletedAtNull(componentDto.getSubModule()));
        }
        createComponent.setComponentName(componentDto.getComponent());
        createComponent.setIcon(componentDto.getIcon());
        createComponent.setPath(componentDto.getPath());
        createComponent.setSortOrder(componentDto.getSortOrder());
        createComponent.setIsActive(componentDto.getIsActive());
        createComponent.setCreatedAt(commonService.getCurrentDateTime());
        createComponent.setCreatedBy(authService.getAuthUserId());
        componentRepository.save(createComponent);
    }

    @Override
    public void update(String uniqueKey, ComponentDto componentDto) {
        Component updateComponent = componentRepository.findByUniqueKeyAndDeletedAtNull(uniqueKey);
        if (!Objects.equals(componentDto.getComponent(), updateComponent.getComponentName())) {
            checkIfModuleAndComponentExists(componentDto);
        }
        if (!Objects.equals(componentDto.getPath(), updateComponent.getPath())) {
            checkIfPathExists(componentDto);
        }
        if (!Objects.equals(componentDto.getSortOrder(), updateComponent.getSortOrder())) {
            checkIfSortOrderExists(componentDto);
        }
        updateComponent.setModule(moduleRepository.findByIdAndDeletedAtNull(componentDto.getModule()));
        if (componentDto.getSubModule() != null) {
            updateComponent.setSubModule(subModuleRepository.findByUniqueKeyAndDeletedAtNull(componentDto.getSubModule()));
        }
        updateComponent.setComponentName(componentDto.getComponent());
        updateComponent.setIcon(componentDto.getIcon());
        updateComponent.setPath(componentDto.getPath());
        updateComponent.setSortOrder(componentDto.getSortOrder());
        updateComponent.setIsActive(componentDto.getIsActive());
        updateComponent.setUpdatedAt(commonService.getCurrentDateTime());
        updateComponent.setUpdatedBy(authService.getAuthUserId());
        componentRepository.save(updateComponent);
    }

    @Override
    public void delete(String uniqueKey) {
        Component deleteComponent = componentRepository.findByUniqueKeyAndDeletedAtNull(uniqueKey);
        deleteComponent.setDeletedAt(commonService.getCurrentDateTime());
        deleteComponent.setDeletedBy(authService.getAuthUserId());
        componentRepository.save(deleteComponent);
    }

    @Override
    public Long count() {
        return componentRepository.countAllByDeletedAtIsNull();
    }

    @Override
    public List<Component> findAll() {
        return componentRepository.findAllByDeletedAtNull();
    }

    @Override
    public List<Component> findAllByModuleId(Long moduleId) {
        return componentRepository.findAllByModuleIdAndDeletedAtNull(moduleId);
    }

    @Override
    public Component findByPath(String path) {
        return componentRepository.findByPathAndDeletedAtNull(path);
    }

    @Override
    public Set<Component> findAllOrphanComponentsByModuleUniqueKey(String moduleUniqueKey, String keyword) throws ExceptionHandlerUtil {
        try {
            if (nullCheck(keyword)) {
                return componentRepository.findLastTenEntriesByModuleUniqueKey(moduleUniqueKey, keyword, PageRequest.of(0, 10));
            } else {
                return componentRepository.findAllByModuleUniqueKeyAndComponentNameAndDeletedAtNullOrderByIdDesc(moduleUniqueKey, PageRequest.of(0, 10));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public Set<Component> findAllBySubModuleId(Long subModuleId) {
        return componentRepository.findAllBySubModuleIdAndDeletedAtNull(subModuleId);
    }

    @SneakyThrows
    private void checkIfModuleAndComponentExists(ComponentDto componentDto) {
        Module module = moduleRepository.findByIdAndDeletedAtNull(componentDto.getModule());
        Component component = componentRepository.findByModuleAndComponentNameAndDeletedAtNull(module, componentDto.getComponent());
        if (component != null) {
            throw new CommonException("Component exists for the given module!");
        }
    }

    @SneakyThrows
    private void checkIfPathExists(ComponentDto ComponentDto) {
        Component Component = componentRepository.findByPathAndDeletedAtNull(ComponentDto.getPath());
        if (Component != null) {
            throw new DuplicateFoundException("Path exists!");
        }
    }

    @SneakyThrows
    private void checkIfSortOrderExists(ComponentDto ComponentDto) {
        Component Component = componentRepository.findByModuleIdAndSortOrderAndDeletedAtNull(ComponentDto.getModule(), ComponentDto.getSortOrder());
        if (Component != null) {
            throw new DuplicateFoundException("Sort Order exists!");
        }
    }

    @Override
    public Set<Component> findAllByKeyword(String parentUniqueKey, String keyword) throws ExceptionHandlerUtil {
        try {
            if (nullCheck(keyword)) {
                return componentRepository.findLastTenEntriesComponentByModuleUniqueKey(parentUniqueKey, keyword, PageRequest.of(0, 10));
            } else {
                return componentRepository.findAllByComponentNameAndDeletedAtNullOrderByIdDesc(parentUniqueKey, PageRequest.of(0, 10));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public Set<Component> findAllComponentBySubModuleUniqueKeyAndKeyword(String parentUniqueKey, String keyword) throws ExceptionHandlerUtil {
        try {
            if (nullCheck(keyword)) {
                return componentRepository.findLastTenEntriesComponentBySubModuleUniqueKey(parentUniqueKey, keyword, PageRequest.of(0, 10));
            } else {
                return componentRepository.findAllBySubModuleUniqueKeyAndComponentNameAndDeletedAtNullOrderByIdDesc(parentUniqueKey, PageRequest.of(0, 10));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public List<Component> findAllLikeUniqueKey(String uniqueKey) {
        return componentRepository.findAllLikeUniqueKey(uniqueKey, PageRequest.of(0, 5));
    }
}
