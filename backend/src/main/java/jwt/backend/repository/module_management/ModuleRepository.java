package jwt.backend.repository.module_management;

import jwt.backend.entity.module_management.Module;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findAllByIsActiveTrueAndDeletedAtNullOrderBySortOrderAsc();

    Set<Module> findAllByDeletedAtNull();

    Set<Module> findAllByIdAndDeletedAtNullAndIsActiveTrue(Long id);

    Module findByIdAndIsActiveTrueAndDeletedAtNull(Long id);

    Module findByUniqueKeyAndDeletedAtNull(String uniqueKey);

    Module findByIdAndDeletedAtNull(Long id);

    Module findByModuleNameAndDeletedAtNull(String moduleName);

    Module findBySortOrderAndDeletedAtNull(Long sortOrder);

    Long countAllByDeletedAtNull();

    @Query("SELECT m FROM Module m WHERE m.moduleName like %:keyword% AND m.deletedAt IS NULL ORDER BY m.id DESC")
    Set<Module> findLastTenEntriesByKeyword(@Param("keyword") String keyword, PageRequest pageRequest);

    @Query("SELECT m FROM Module m WHERE m.deletedAt IS NULL ORDER BY m.id DESC")
    Set<Module> findAllByModuleNameAndDeletedAtNullOrderByIdDesc(PageRequest pageRequest);

    @Query("SELECT m FROM Module m WHERE lower(m.uniqueKey) like :uniqueKey AND m.deletedAt IS NULL ORDER BY m.id DESC")
    List<Module> findAllLikeUniqueKey(@Param("uniqueKey") String uniqueKey, PageRequest pageRequest);
}