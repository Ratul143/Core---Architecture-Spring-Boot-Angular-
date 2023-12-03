package jwt.backend.service.module_management;

import jwt.backend.dto.module_management.SubModuleDto;
import jwt.backend.entity.module_management.SubModule;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.exception.ExceptionHandlerUtil;
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jwt.backend.service.CommonServiceImpl.nullCheck;

@Service
@Transactional
@RequiredArgsConstructor
public class SubModuleServiceImpl implements SubModuleService {
    private final SubModuleRepository subModuleRepository;
    private final ModuleRepository moduleRepository;
    private final AuthService authService;
    private final CommonService commonService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResult<SubModule> list(String search, Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubModule> criteriaQuery = criteriaBuilder.createQuery(SubModule.class);
        Root<SubModule> root = criteriaQuery.from(SubModule.class);
        Predicate predicate = criteriaBuilder.conjunction();

        if (search != null && !search.isEmpty()) {
            Predicate modulePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("module").get("moduleName")), "%" + search.toLowerCase() + "%");
            Predicate subModulePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("subModuleName")), "%" + search.toLowerCase() + "%");
            Predicate iconPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("icon")), "%" + search.toLowerCase() + "%");
            predicate = criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.or(modulePredicate, subModulePredicate, iconPredicate)
            );
        }
        Predicate deletedAtIsNull = criteriaBuilder.isNull(root.get("deletedAt"));
        predicate = criteriaBuilder.and(predicate, deletedAtIsNull);
        criteriaQuery.where(predicate).orderBy(criteriaBuilder.asc(root.get("sortOrder")));
        TypedQuery<SubModule> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        return new PageResult<>(typedQuery.getResultList(), commonService.getCountWithPredicate(predicate, SubModule.class));
    }

    @Override
    public void create(SubModuleDto subModuleDto) {
        checkIfModuleAndSubModuleExists(subModuleDto);
        checkIfSortOrderExists(subModuleDto.getSortOrder());
        SubModule createSubModule = new SubModule();
        createSubModule.setUniqueKey(commonService.randomAlphaNumeric());
        createSubModule.setModule(moduleRepository.findByUniqueKeyAndDeletedAtNull(subModuleDto.getModule()));
        createSubModule.setSubModuleName(subModuleDto.getSubModuleName());
        createSubModule.setIcon(subModuleDto.getIcon());
        createSubModule.setIsActive(subModuleDto.getIsActive());
        createSubModule.setSortOrder(subModuleDto.getSortOrder());
        createSubModule.setCreatedAt(commonService.getCurrentDateTime());
        createSubModule.setCreatedBy(authService.getAuthUserId());
        subModuleRepository.save(createSubModule);
    }

    @SneakyThrows
    public void checkIfModuleAndSubModuleExists(SubModuleDto SubModuleDto) {
        SubModule subModule = subModuleRepository.findByModuleAndSubModuleNameAndDeletedAtNull(moduleRepository.findByUniqueKeyAndDeletedAtNull(SubModuleDto.getModule()), SubModuleDto.getSubModuleName());
        if (subModule != null) {
            throw new DuplicateFoundException("Sub Module exists for the given module!");
        }
    }

    @SneakyThrows
    public void checkIfSortOrderExists(Long sortOrder) {
        SubModule subModule = subModuleRepository.findBySortOrderAndDeletedAtNull(sortOrder);
        if (subModule != null) {
            throw new DuplicateFoundException("Sort order exists!");
        }
    }

    @Override
    public void update(String uniqueKey, SubModuleDto subModuleDto) {
        SubModule updateSubModule = findSubModuleByUniqueKey(uniqueKey);
        if (!Objects.equals(updateSubModule.getSubModuleName(), subModuleDto.getSubModuleName())) {
            checkIfModuleAndSubModuleExists(subModuleDto);
        }

        if (!Objects.equals(updateSubModule.getSortOrder(), subModuleDto.getSortOrder())) {
            checkIfSortOrderExists(subModuleDto.getSortOrder());
        }
        updateSubModule.setModule(moduleRepository.findByUniqueKeyAndDeletedAtNull(subModuleDto.getModule()));
        updateSubModule.setSubModuleName(subModuleDto.getSubModuleName());
        updateSubModule.setIcon(subModuleDto.getIcon());
        updateSubModule.setIsActive(subModuleDto.getIsActive());
        updateSubModule.setSortOrder(subModuleDto.getSortOrder());
        updateSubModule.setUpdatedAt(commonService.getCurrentDateTime());
        updateSubModule.setUpdatedBy(authService.getAuthUserId());
        subModuleRepository.save(updateSubModule);
    }

    @Override
    public SubModule findSubModuleById(Long id) {
        return subModuleRepository.findByIdAndIsActiveTrueAndDeletedAtNull(id);
    }

    @Override
    public SubModule findSubModuleByUniqueKey(String uniqueKey) {
        return subModuleRepository.findByUniqueKeyAndDeletedAtNull(uniqueKey);
    }

    @Override
    public void delete(String moduleName) {
        SubModule SubModule = findSubModuleByUniqueKey(moduleName);
        SubModule.setDeletedAt(commonService.getCurrentDateTime());
        SubModule.setDeletedBy(authService.getAuthUserId());
        subModuleRepository.save(SubModule);
    }

    @Override
    public Long count() {
        return subModuleRepository.countAllByDeletedAtNull();
    }

    @Override
    public List<SubModule> findAll() {
        return subModuleRepository.findAllByIsActiveTrueAndDeletedAtNullOrderBySortOrderAsc();
    }

    @Override
    public Set<SubModule> findAllSubModulesByModuleId(Long moduleId) {
        return subModuleRepository.findAllByModuleIdAndDeletedAtNull(moduleId);
    }

    @Override
    public Set<SubModule> findAllByKeyword(String parentUniqueKey, String keyword) throws ExceptionHandlerUtil {
        try {
            Set<SubModule> existingSubModule;
            if (nullCheck(keyword)) {
                existingSubModule = subModuleRepository.findLastTenEntriesSubModuleByModuleUniqueKey(parentUniqueKey, keyword, PageRequest.of(0, 10));
            } else {
                existingSubModule = subModuleRepository.findAllBySubModuleNameAndDeletedAtNullOrderByIdDesc(parentUniqueKey, PageRequest.of(0, 10));
            }
            return existingSubModule;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    public List<SubModule> findAllLikeUniqueKey(String uniqueKey) {
        return subModuleRepository.findAllLikeUniqueKey(uniqueKey, PageRequest.of(0, 5));
    }
}
