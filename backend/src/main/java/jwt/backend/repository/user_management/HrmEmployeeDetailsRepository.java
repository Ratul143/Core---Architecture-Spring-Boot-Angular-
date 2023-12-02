package jwt.backend.repository.user_management;

import jwt.backend.entity.production.accs_order.HrmEmployeeView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HrmEmployeeDetailsRepository extends JpaRepository<HrmEmployeeView, Long> {

    List<HrmEmployeeView> findAllByCreatedDateIsNotNull();
}
