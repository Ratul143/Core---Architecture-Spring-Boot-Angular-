package jwt.backend.service.module_management;


import jwt.backend.dto.MenuContainer;
import jwt.backend.dto.module_management.RoleBasedPermissionDto;
import jwt.backend.entity.module_management.Module;
import jwt.backend.entity.module_management.*;
import jwt.backend.entity.user_management.Role;
import jwt.backend.exception.CommonException;
import jwt.backend.exception.ExceptionHandlerUtil;
import jwt.backend.repository.module_management.*;
import jwt.backend.repository.user_management.RoleRepository;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.PageResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

import static jwt.backend.service.CommonServiceImpl.nullCheck;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleBasedPermissionServiceImpl implements RoleBasedPermissionService {

    private final RoleBasedPermissionRepository roleBasedPermissionRepository;
    private final UserBasedPermissionRepository userBasedPermissionRepository;
    private final RoleRepository roleRepository;
    private final ModuleRepository moduleRepository;
    private final AuthService authService;
    private final CommonService commonService;
    private final ComponentRepository componentRepository;
    private final SubModuleRepository subModuleRepository;

    private final EntityManager entityManager;

    @Override
    public void createOrUpdate(String roleUniqueKey, List<RoleBasedPermissionDto> roleBasedPermission) throws ExceptionHandlerUtil {
        try {
            roleBasedPermission.forEach(elem -> {
                if (elem.getUniqueKey() != null && !elem.getUniqueKey().isEmpty()) {
                    RoleBasedPermission findRoleBasedPermission = roleBasedPermissionRepository.findByUniqueKeyAndDeletedAtNull(elem.getUniqueKey());
                    if (findRoleBasedPermission.getCreate() != elem.getCreate()) {
                        findRoleBasedPermission.setCreate(elem.getCreate());
                        findRoleBasedPermission.setUpdatedAt(commonService.getCurrentDateTime());
                        findRoleBasedPermission.setUpdatedBy(authService.getAuthUserId());
                    }
                    if (findRoleBasedPermission.getRead() != elem.getRead()) {
                        findRoleBasedPermission.setRead(elem.getRead());
                        findRoleBasedPermission.setUpdatedAt(commonService.getCurrentDateTime());
                        findRoleBasedPermission.setUpdatedBy(authService.getAuthUserId());
                    }
                    if (findRoleBasedPermission.getUpdate() != elem.getUpdate()) {
                        findRoleBasedPermission.setUpdate(elem.getUpdate());
                        findRoleBasedPermission.setUpdatedAt(commonService.getCurrentDateTime());
                        findRoleBasedPermission.setUpdatedBy(authService.getAuthUserId());
                    }
                    if (findRoleBasedPermission.getDelete() != elem.getDelete()) {
                        findRoleBasedPermission.setDelete(elem.getDelete());
                        findRoleBasedPermission.setUpdatedAt(commonService.getCurrentDateTime());
                        findRoleBasedPermission.setUpdatedBy(authService.getAuthUserId());
                    }
                    roleBasedPermissionRepository.save(findRoleBasedPermission);
                } else {
                    RoleBasedPermission findRoleBasedPermission = roleBasedPermissionRepository.findByRoleUniqueKeyAndComponentUniqueKeyAndDeletedAtNull(roleUniqueKey, elem.getComponentUniqueKey());
                    if (findRoleBasedPermission != null) {
                        throw new CommonException("Component assigned to this role");
                    }
                    RoleBasedPermission create = new RoleBasedPermission();
                    create.setUniqueKey(commonService.randomAlphaNumeric());
                    create.setRole(findRoleByUniqueKey(roleUniqueKey));
                    create.setModule(findModuleByUniqueKey(elem));
                    create.setSubModule(findSubModuleByUniqueKey(elem));
                    create.setComponent(findComponentByUniqueKey(elem));
                    create.setCreate(elem.getCreate());
                    create.setRead(elem.getRead());
                    create.setUpdate(elem.getUpdate());
                    create.setDelete(elem.getDelete());
                    create.setCreatedAt(this.commonService.getCurrentDateTime());
                    create.setCreatedBy(this.authService.getAuthUserId());
                    roleBasedPermissionRepository.save(create);
                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    private void updateUserBasedPermission(Long role, RoleBasedPermissionDto component) {
        UserBasedPermission updateUserBasedPermission = userBasedPermissionRepository.findByRoleIdAndComponentUniqueKeyAndDeletedAtNull(role, component.getComponentUniqueKey());
        if (updateUserBasedPermission != null) {
            updateUserBasedPermission.setCreate(component.getCreate());
            updateUserBasedPermission.setRead(component.getRead());
            updateUserBasedPermission.setUpdate(component.getUpdate());
            updateUserBasedPermission.setDelete(component.getDelete());
            userBasedPermissionRepository.save(updateUserBasedPermission);
        }
    }

    @Override
    public List<RoleBasedPermission> findAll() {
        return roleBasedPermissionRepository.findAllByDeletedAtNull();
    }

    @Override
    public MenuContainer findAllSideNavElements() {
        Set<Module> moduleSet = new HashSet<>();
        Set<SubModule> subModuleSet = new HashSet<>();
        Set<Component> componentSet = new HashSet<>();
        Set<Object> permissions = new HashSet<>();
        Set<UserBasedPermission> userBasedPermissions = userBasedPermissionRepository.findAllByUserIdAndDeletedAtNull(authService.getAuthUserId());
        permissions.addAll(userBasedPermissions);
        for (UserBasedPermission userBasedPermission : userBasedPermissions) {
            if (userBasedPermission.getModule() != null) {
                moduleSet.addAll(moduleRepository.findAllByIdAndDeletedAtNullAndIsActiveTrue(userBasedPermission.getModule().getId()));
            }
            if (userBasedPermission.getSubModule() != null) {
                subModuleSet.addAll(subModuleRepository.findAllByIdAndDeletedAtNullAndIsActiveTrue(userBasedPermission.getSubModule().getId()));
            }
            if (userBasedPermission.getComponent() != null) {
                componentSet.addAll(componentRepository.findAllByIdAndDeletedAtNullAndIsActiveTrue(userBasedPermission.getComponent().getId()));
            }
        }

        Long roleId = authService.getAuthUser().getRole().getId();
        Set<RoleBasedPermission> roleBasedPermissions = roleBasedPermissionRepository.findAllByRoleIdAndDeletedAtNull(roleId);
        permissions.addAll(roleBasedPermissions);
        for (RoleBasedPermission roleBasedPermission : roleBasedPermissions) {
            if (roleBasedPermission.getModule() != null) {
                moduleSet.addAll(moduleRepository.findAllByIdAndDeletedAtNullAndIsActiveTrue(roleBasedPermission.getModule().getId()));
            }
            if (roleBasedPermission.getSubModule() != null) {
                subModuleSet.addAll(subModuleRepository.findAllByIdAndDeletedAtNullAndIsActiveTrue(roleBasedPermission.getSubModule().getId()));
            }
            Component component = roleBasedPermission.getComponent();
            if (component != null && !componentSet.contains(component)) {
                component = componentRepository.findByIdAndDeletedAtNullAndIsActiveTrue(component.getId());
                if (component != null) {
                    componentSet.add(component);
                }
            }
        }

        MenuContainer menuContainer = new MenuContainer();
        menuContainer.setModules(moduleSet.stream().sorted(Comparator.comparing(Module::getSortOrder)).collect(Collectors.toCollection(LinkedHashSet::new)));
        menuContainer.setSubModules(subModuleSet.stream().sorted(Comparator.comparing(SubModule::getSortOrder)).collect(Collectors.toCollection(LinkedHashSet::new)));
        menuContainer.setComponents(componentSet.stream().sorted(Comparator.comparing(Component::getSortOrder)).collect(Collectors.toCollection(LinkedHashSet::new)));
        menuContainer.setPermissions(permissions);
        return menuContainer;
    }

    @Override
    public PageResult<RoleBasedPermission> findAllByUniqueKey(String roleUniqueKey, String searchByModule, String searchBySubModule, String searchByComponent, String searchByAny, Integer page, Integer size) throws ExceptionHandlerUtil {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<RoleBasedPermission> query = criteriaBuilder.createQuery(RoleBasedPermission.class);
            Root<RoleBasedPermission> root = query.from(RoleBasedPermission.class);
            Predicate predicate = criteriaBuilder.conjunction();
            if (nullCheck(searchByModule)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("module").get("uniqueKey"), searchByModule));
            }

            if (nullCheck(searchBySubModule)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("subModule").get("uniqueKey"), searchBySubModule));
            }

            if (nullCheck(searchByComponent)) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("component").get("uniqueKey"), searchByComponent));
            }

            if (nullCheck(searchByAny)) {
                String searchPattern = "%" + searchByComponent + "%";
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("module").get("moduleName")), searchPattern), criteriaBuilder.like(criteriaBuilder.lower(root.get("subModule").get("subModuleName")), searchPattern)), criteriaBuilder.like(criteriaBuilder.lower(root.get("component").get("componentName")), searchPattern));
            }

            Predicate roleIdPredicate = criteriaBuilder.equal(root.get("role").get("uniqueKey"), roleUniqueKey);
            Predicate deletedAtIsNull = criteriaBuilder.isNull(root.get("deletedAt"));
            predicate = criteriaBuilder.and(predicate, roleIdPredicate, deletedAtIsNull);
            query.where(predicate);
            query.orderBy(criteriaBuilder.desc(root.get("id")));
            TypedQuery<RoleBasedPermission> typedQuery = entityManager.createQuery(query);
            typedQuery.setFirstResult(page * size);
            typedQuery.setMaxResults(size);
            return new PageResult<>(typedQuery.getResultList(), commonService.getCountWithPredicate(predicate, RoleBasedPermission.class));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    private Component findComponentByUniqueKey(RoleBasedPermissionDto elem) {
        return componentRepository.findByUniqueKeyAndDeletedAtNull(elem.getComponentUniqueKey());
    }

    private SubModule findSubModuleByUniqueKey(RoleBasedPermissionDto elem) {
        return subModuleRepository.findByUniqueKeyAndDeletedAtNull(elem.getSubModuleUniqueKey());
    }

    private Module findModuleByUniqueKey(RoleBasedPermissionDto elem) {
        return moduleRepository.findByUniqueKeyAndDeletedAtNull(elem.getModuleUniqueKey());
    }

    private Role findRoleByUniqueKey(String roleUniqueKey) {
        return roleRepository.findByUniqueKeyAndDeletedAtNull(roleUniqueKey);
    }
}
