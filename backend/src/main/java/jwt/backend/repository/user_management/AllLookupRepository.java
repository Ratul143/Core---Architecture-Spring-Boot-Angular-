package jwt.backend.repository.user_management;

import jwt.backend.entity.production.accs_order.AccsCustomerView;
import jwt.backend.entity.production.accs_order.AllLookupView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllLookupRepository extends JpaRepository<AllLookupView, Long> {
    Optional<AllLookupView> findByIdAndIsActive(Long id, boolean isDeleted);
}
