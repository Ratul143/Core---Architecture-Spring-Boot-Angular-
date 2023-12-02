package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.ApprovalWorkFlowStepsDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowSteps;

import java.util.List;

public interface ApprovalWorkFlowStepsService {
    void createApprovalFlowSteps(ApprovalWorkFlowStepsDto approvalWorkFlowStepsDto);

    List<ApprovalWorkFlowSteps> getApprovalWorkFlowStepsPaginate(String formName, String statusFrom, String statusTo, Integer page, Integer size);

    void updateApprovalFlowSteps(Long formId, ApprovalWorkFlowSteps approvalWorkFlowSteps);

    void deleteApprovalWorkFlowSteps(Long id);

    List<ApprovalWorkFlowSteps> findAll();

    ApprovalWorkFlowSteps getApprovalWorkFlowStepsById(Long id);

    Long count();

    List<ApprovalWorkFlowSteps> findApprovalWorkFlowStepsByFormId(Long id);

    ApprovalWorkFlowSteps findByApprovalWorkFlowForIdAndNextWorkFlowStatusId(Long flowForId, Long nextStatusId);

    ApprovalWorkFlowSteps findByApprovalWorkFlowForIdAndCurrentWorkFlowStatusId(Long flowForId, Long currentStatusId);

    ApprovalWorkFlowSteps findNextStep(Long flowForId, Long statusId);

    ApprovalWorkFlowSteps findPreviousStep(Long flowForId, Long statusId);
}
