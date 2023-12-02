package jwt.backend.repository.user_management;

import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccsAuthUserRepository extends JpaRepository<Accs_Auth_User, Long> {

    Accs_Auth_User findUserByUsername(String name);

    Optional<Accs_Auth_User> findByIdAndIsDeleted(Long id, Boolean isDeleted);

    Optional<Accs_Auth_User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);

    Long countAllByDeletedAtNull();

    Accs_Auth_User findUserByEmail(String email);

    Accs_Auth_User findByIdAndDeletedAtNull(Long id);

    Accs_Auth_User findByIdAndIsDeletedFalseAndEnabledTrueAndAccountLockedFalse(Long id);

    Optional<Accs_Auth_User> findByIsDeletedAndId(Boolean isDeleted, Long id);

    boolean existsByIsDeletedAndEmail(Boolean isDeleted, String email);

    boolean existsByIsDeletedAndUsername(Boolean isDeleted, String username);
}
