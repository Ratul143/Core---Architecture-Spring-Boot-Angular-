package jwt.backend.repository;


import jwt.backend.entity.user_management.HREmployeeView;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jaber
 * @date 7/21/2022
 * @time 11:00 AM
 */
public interface HREmployeeViewRepository extends JpaRepository<HREmployeeView, Long> {
}

