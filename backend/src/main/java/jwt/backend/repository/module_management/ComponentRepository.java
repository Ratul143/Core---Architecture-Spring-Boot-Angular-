package jwt.backend.repository.module_management;

import jwt.backend.entity.module_management.Component;
import jwt.backend.entity.module_management.Module;
import jwt.backend.entity.module_management.SubModule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ComponentRepository extends JpaRepository<Component, Long> {

    List<Component> findAllByDeletedAtNull();

    Set<Component> findAllBySubModuleIdAndDeletedAtNull(Long id);

    Set<Component> findAllByIdAndDeletedAtNullAndIsActiveTrue(Long id);

    Component findByUniqueKeyAndDeletedAtNull(String uniqueKey);

    Component findByModuleAndSubModuleAndComponentNameAndDeletedAtNull(Module module, SubModule subModule, String componentName);

    Component findByModuleAndComponentNameAndDeletedAtNull(Module module, String componentName);

    Component findByPathAndDeletedAtNull(String path);

    Component findByModuleIdAndSortOrderAndDeletedAtNull(Long moduleId, Long sortOrder);

    List<Component> findAllByModuleIdAndDeletedAtNull(Long moduleId);

    List<Component> findAllByModuleIdAndDeletedAtNullAndIsActiveTrue(Long moduleId);

    Set<Component> findAllByModuleIdAndSubModuleIsNullAndDeletedAtNull(Long moduleId);

    List<Component> findAllByModuleIdAndSubModuleIdNullAndDeletedAtNull(Long moduleId);

    List<Component> findAllByModuleIdAndSubModuleIdAndDeletedAtNull(Long moduleId, Long subModuleId);

    Integer countAllByModuleIdAndDeletedAtNull(Long moduleId);

    Long countAllByDeletedAtIsNull();

    Component findByIdAndDeletedAtNull(Long id);

    Component findByIdAndDeletedAtNullAndIsActiveTrue(Long id);

    Component findBySubModuleIdAndDeletedAtNull(Long moduleId);

    Component findByModuleIdAndDeletedAtNull(Long moduleId);

    @Query("SELECT c FROM Component c LEFT JOIN Module qem ON c.module.id = qem.id " +
            " WHERE c.componentName like %:keyword% AND qem.uniqueKey = :parentUniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    Set<Component> findLastTenEntriesComponentByModuleUniqueKey(@Param("parentUniqueKey") String parentUniqueKey, @Param("keyword") String keyword, PageRequest pageRequest);

    @Query("SELECT c FROM Component c LEFT JOIN Module qem ON c.module.id = qem.id " +
            " WHERE qem.uniqueKey = :parentUniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    Set<Component> findAllByComponentNameAndDeletedAtNullOrderByIdDesc(@Param("parentUniqueKey") String parentUniqueKey, PageRequest pageRequest);

    @Query("SELECT c FROM Component c LEFT JOIN SubModule sm ON c.subModule.id = sm.id " +
            " WHERE c.componentName like %:keyword% AND sm.uniqueKey = :parentUniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    Set<Component> findLastTenEntriesComponentBySubModuleUniqueKey(@Param("parentUniqueKey") String parentUniqueKey, @Param("keyword") String keyword, PageRequest pageRequest);

    @Query("SELECT c FROM Component c LEFT JOIN SubModule sm ON c.subModule.id = sm.id " +
            " WHERE sm.uniqueKey = :parentUniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    Set<Component> findAllBySubModuleUniqueKeyAndComponentNameAndDeletedAtNullOrderByIdDesc(@Param("parentUniqueKey") String parentUniqueKey, PageRequest pageRequest);

    @Query("SELECT c FROM Component c LEFT JOIN Module m ON c.module.id = m.id " +
            " WHERE c.componentName like %:keyword% AND m.uniqueKey = :moduleUniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    Set<Component> findLastTenEntriesByModuleUniqueKey(@Param("moduleUniqueKey") String moduleUniqueKey, @Param("keyword") String keyword, PageRequest pageRequest);

    @Query("SELECT c FROM Component c LEFT JOIN Module m ON c.module.id = m.id " +
            " WHERE m.uniqueKey = :moduleUniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    Set<Component> findAllByModuleUniqueKeyAndComponentNameAndDeletedAtNullOrderByIdDesc(@Param("moduleUniqueKey") String moduleUniqueKey, PageRequest pageRequest);

    @Query("SELECT c FROM Component c WHERE lower(c.uniqueKey) like :uniqueKey AND c.deletedAt IS NULL ORDER BY c.id DESC")
    List<Component> findAllLikeUniqueKey(@Param("uniqueKey") String uniqueKey, PageRequest pageRequest);
}