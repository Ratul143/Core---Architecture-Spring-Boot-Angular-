package jwt.backend.repository.settings.approval_flow;

import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlow;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ApprovalWorkFlowRepository extends JpaRepository<ApprovalWorkFlow, Long> {
    List<ApprovalWorkFlow> findAllByDeletedAtNull();

    @Query("select awf from ApprovalWorkFlow awf where awf.deletedAt is null and ((lower(awf.approvalWorkFlowFor.flowFor) like %:searchValueForForm%) and (lower(awf.approvalWorkFlowStatus.statusName) like %:searchValueForStatus%) and (lower(awf.approvalWorkFlowStatus.code) like %:searchValueForCode%)) order by awf.id asc")
    List<ApprovalWorkFlow> findApprovalWorkFlowList(@Param("searchValueForForm") String searchValueForForm, @Param("searchValueForStatus") String searchValueForStatus, @Param("searchValueForCode") String searchValueForCode, PageRequest pageable);

    ApprovalWorkFlow findByIdAndDeletedAtNull(Long id);

    Long countAllByDeletedAtIsNull();

    //    ApprovalWorkFlow findByCodeAndDeletedAtNull(String code);
    Set<ApprovalWorkFlow> findAllByApprovalWorkFlowForIdAndDeletedAtNull(Long formId);

    ApprovalWorkFlow findByIdAndApprovalWorkFlowForIdAndDeletedAtNull(Long id, Long formId);

    ApprovalWorkFlow findByApprovalWorkFlowForIdAndApprovalWorkFlowStatusIdAndDeletedAtNull(Long formId, Long statusId);
}