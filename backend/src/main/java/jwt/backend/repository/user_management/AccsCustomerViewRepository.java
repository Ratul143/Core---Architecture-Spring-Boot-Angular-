package jwt.backend.repository.user_management;

import jwt.backend.entity.production.accs_order.AccsCustomerView;
import jwt.backend.entity.user_management.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccsCustomerViewRepository  extends JpaRepository<AccsCustomerView, Long> {
    Optional<AccsCustomerView> findByIdAndIsDeleted(Long id, boolean isDeleted);
}
