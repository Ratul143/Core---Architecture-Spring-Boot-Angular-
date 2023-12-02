package jwt.backend.repository.settings.approval_flow;

import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalWorkFlowLogRepository extends JpaRepository<ApprovalWorkFlowLog, Long> {

    List<ApprovalWorkFlowLog> findAllByUniqueIdOrderByIdDesc(String uniqueId);
}