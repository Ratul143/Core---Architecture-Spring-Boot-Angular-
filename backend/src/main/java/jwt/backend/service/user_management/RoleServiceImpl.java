package jwt.backend.service.user_management;

import jwt.backend.dto.user_management.RoleDto;
import jwt.backend.entity.user_management.Role;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.repository.user_management.RoleRepository;
import jwt.backend.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AuthService authService;
    private final CommonService commonService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createRole(RoleDto roleDto) {
        checkIfRoleExists(roleDto.getRole());
        Role createRole = new Role();
        createRole.setUniqueKey(commonService.randomAlphaNumeric());
        createRole.setRole(roleDto.getRole());
        createRole.setAuthorities(roleDto.getAuthorities());
        createRole.setCreatedBy(authService.getAuthUserId());
        roleRepository.save(createRole);
    }

    @SneakyThrows
    public void checkIfRoleExists(String role) {
        Role findRole = roleRepository.findByRoleAndDeletedAtNull(role);
        if (findRole != null) {
            throw new DuplicateFoundException("Role exists!");
        }
    }

    @Override
    public PageResult<Role> roleList(String search, Integer page, Integer size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        Predicate predicate = criteriaBuilder.conjunction();
        Predicate deletedAtIsNull = criteriaBuilder.isNull(root.get("deletedAt"));
        if (search != null && !search.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("role")), "%" + search + "%"));
        }

        predicate = criteriaBuilder.and(predicate, deletedAtIsNull);
        criteriaQuery.where(predicate).orderBy(criteriaBuilder.desc(root.get("id")));
        TypedQuery<Role> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        return new PageResult<>(typedQuery.getResultList(), commonService.getCountWithPredicate(predicate, Role.class));
    }

    @Override
    public void updateRole(String uniqueKey, RoleDto roleDto) {
        Role updateRole = findRoleByUniqueKey(uniqueKey);
        if (!Objects.equals(updateRole.getRole(), roleDto.getRole())) {
            checkIfRoleExists(roleDto.getRole());
        }
        updateRole.setRole(roleDto.getRole());
        updateRole.setAuthorities(roleDto.getAuthorities());
        updateRole.setUpdatedAt(commonService.getCurrentDateTime());
        updateRole.setUpdatedBy(authService.getAuthUserId());
        roleRepository.save(updateRole);
    }

    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findByIdAndDeletedAtNull(id);
    }

    @Override
    public Role findRoleByUniqueKey(String uniqueKey) {
        return roleRepository.findByUniqueKeyAndDeletedAtNull(uniqueKey);
    }

    @Override
    public void deleteRole(String uniqueKey) {
        Role findRole = findRoleByUniqueKey(uniqueKey);
        findRole.setDeletedAt(commonService.getCurrentDateTime());
        findRole.setDeletedBy(authService.getAuthUserId());
        roleRepository.save(findRole);
    }

    @Override
    public Long count() {
        return roleRepository.countAllByDeletedAtNull();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAllByDeletedAtNull();
    }

    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRoleAndDeletedAtNull(role);
    }

}
