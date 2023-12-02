package jwt.backend.repository.settings.approval_flow;

import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowLog;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalWorkFlowForRepository extends JpaRepository<ApprovalWorkFlowFor, Long> {
    List<ApprovalWorkFlowFor> findAllByDeletedAtNull();

    @Query("select awff from ApprovalWorkFlowFor awff where awff.deletedAt is null and ((lower(awff.flowFor) like %:approvalWorkFlowFormName%) and (lower(awff.code) like %:approvalWorkFlowFormCode%)) order by awff.id desc")
    List<ApprovalWorkFlowFor> getApprovalWorkFlowForList(@Param("approvalWorkFlowFormName") String approvalWorkFlowFormName, @Param("approvalWorkFlowFormCode") String approvalWorkFlowFormCode, PageRequest pageable);

    ApprovalWorkFlowFor findByIdAndDeletedAtNull(Long id);

    Long countAllByDeletedAtIsNull();

    ApprovalWorkFlowFor findByCodeAndDeletedAtNull(String code);
    ApprovalWorkFlowFor findByFlowForAndDeletedAtNull(String code);

    interface ApprovalWorkFlowLogRepository extends JpaRepository<ApprovalWorkFlowLog, Long> {
        List<ApprovalWorkFlowLog> findAllByRequisitionUniqueKeyOrderByIdDesc(String uniqueRequisitionId);
    }
}