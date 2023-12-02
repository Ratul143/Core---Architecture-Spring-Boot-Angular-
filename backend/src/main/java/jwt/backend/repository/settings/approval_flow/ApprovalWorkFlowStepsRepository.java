package jwt.backend.repository.settings.approval_flow;

import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowSteps;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalWorkFlowStepsRepository extends JpaRepository<ApprovalWorkFlowSteps, Long> {
    List<ApprovalWorkFlowSteps> findAllByDeletedAtNull();

    @Query("select awfs from ApprovalWorkFlowSteps awfs where awfs.deletedAt is null and ((lower(awfs.approvalWorkFlowFor.flowFor) like %:formName%) and (lower(awfs.currentWorkFlowStatus.statusName) like %:statusFrom%) and (lower(awfs.nextWorkFlowStatus.statusName) like %:statusTo%)) order by awfs.id asc")
    List<ApprovalWorkFlowSteps> getApprovalWorkFlowStepsList(@Param("formName") String formName, @Param("statusFrom") String statusFrom, @Param("statusTo") String statusTo, PageRequest pageable);

    ApprovalWorkFlowSteps findByIdAndDeletedAtNull(Long id);

    ApprovalWorkFlowSteps findByApprovalWorkFlowForIdAndNextWorkFlowStatusIdAndDeletedAtNull(Long flowForId, Long nextWorkFlowId);

    ApprovalWorkFlowSteps findByApprovalWorkFlowForIdAndCurrentWorkFlowStatusIdAndDeletedAtNull(Long flowForId, Long currentWorkFlowId);

    Long countAllByDeletedAtIsNull();

    List<ApprovalWorkFlowSteps> findAllByApprovalWorkFlowForIdAndDeletedAtNull(Long formId);

    ApprovalWorkFlowSteps findAllByApprovalWorkFlowForIdAndCurrentWorkFlowStatusIdAndNextWorkFlowStatusIdAndDeletedAtNull(Long formId, Long currentWorkFlowStatusId, Long nextWorkFlowStatusId);


}