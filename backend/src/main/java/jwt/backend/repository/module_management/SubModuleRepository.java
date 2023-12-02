package jwt.backend.repository.module_management;

import jwt.backend.entity.module_management.Module;
import jwt.backend.entity.module_management.SubModule;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SubModuleRepository extends JpaRepository<SubModule, Long> {

    List<SubModule> findAllByIsActiveTrueAndDeletedAtNullOrderBySortOrderAsc();

    Set<SubModule> findAllByIdAndDeletedAtNullAndIsActiveTrue(Long id);

    SubModule findByIdAndIsActiveTrueAndDeletedAtNull(Long id);

    SubModule findByUniqueKeyAndDeletedAtNull(String uniqueKey);

    SubModule findBySortOrderAndDeletedAtNull(Long sortOrder);

    Long countAllByDeletedAtNull();

    SubModule findByModuleAndSubModuleNameAndDeletedAtNull(Module module, String subModuleName);

    Set<SubModule> findAllByModuleIdAndDeletedAtNull(Long moduleId);

    SubModule findByIdAndDeletedAtNull(Long id);

    @Query("SELECT sm FROM SubModule sm LEFT JOIN Module qem ON sm.module.id = qem.id " +
            " WHERE sm.subModuleName like %:keyword% AND qem.uniqueKey = :parentUniqueKey AND sm.deletedAt IS NULL ORDER BY sm.id DESC")
    Set<SubModule> findLastTenEntriesSubModuleByModuleUniqueKey(@Param("parentUniqueKey") String parentUniqueKey, @Param("keyword") String keyword, PageRequest pageRequest);

    @Query("SELECT sm FROM SubModule sm LEFT JOIN Module qem ON sm.module.id = qem.id " +
            " WHERE qem.uniqueKey = :parentUniqueKey AND sm.deletedAt IS NULL ORDER BY sm.id DESC")
    Set<SubModule> findAllBySubModuleNameAndDeletedAtNullOrderByIdDesc(@Param("parentUniqueKey") String parentUniqueKey, PageRequest pageRequest);

    @Query("SELECT m FROM Module m WHERE m.moduleName like %:keyword% AND m.deletedAt IS NULL ORDER BY m.id DESC")
    Set<SubModule> findLastTenEntriesByKeyword(@Param("keyword") String keyword, PageRequest pageRequest);

    @Query("SELECT sm FROM SubModule sm WHERE lower(sm.uniqueKey) like :uniqueKey AND sm.deletedAt IS NULL ORDER BY sm.id DESC")
    List<SubModule> findAllLikeUniqueKey(@Param("uniqueKey") String uniqueKey, PageRequest pageRequest);

}