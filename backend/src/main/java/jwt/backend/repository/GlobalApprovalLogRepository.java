package jwt.backend.repository;

import jwt.backend.entity.GlobalApprovalLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalApprovalLogRepository extends JpaRepository<GlobalApprovalLog, Long> {
    List<GlobalApprovalLog> findAllByFormUniqueIdOrderByIdDesc(String formUniqueId);
}
