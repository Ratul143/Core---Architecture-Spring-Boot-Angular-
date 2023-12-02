package jwt.backend.repository.user_management;

import jwt.backend.entity.user_management.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.deletedAt is null and ((lower(r.role) like %:search%)) order by r.id desc")
    List<Role> findRoleList(@Param("search") String search, PageRequest pageable);

    List<Role> findAllByIdAndDeletedAtNull(Long id);

    List<Role> findAllByDeletedAtNull();

    Long countAllByDeletedAtNull();

    Role findByRoleAndDeletedAtNull(String role);

    Role findByIdAndDeletedAtNull(Long id);

    Role findByUniqueKeyAndDeletedAtNull(String uniqueKey);

   Optional<Role> findByIdAndDeletedAt(Long id, Date deletedAt);
}