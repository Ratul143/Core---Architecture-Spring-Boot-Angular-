package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.ApprovalWorkFlowDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlow;

import java.util.List;
import java.util.Set;

public interface ApprovalWorkFlowService {

    void createApprovalFlowFor(ApprovalWorkFlowDto approvalWorkFlowDto);

    List<ApprovalWorkFlow> findApprovalWorkFlowPaginate(String approvalWorkFlowName, String approvalWorkFlowStatus, String approvalWorkFlowCode, Integer page, Integer size);

    void updateApprovalFlowFor(Long approvalWorkFlowId, ApprovalWorkFlowDto approvalWorkFlowDto);

    void deleteApprovalWorkFlow(Long approvalWorkFlowId);

    List<ApprovalWorkFlow> findAll();

    ApprovalWorkFlow findApprovalWorkFlowById(Long id);

    Long count();

    Set<ApprovalWorkFlow> getApprovalWorkFlowStatusByForm(Long id);

    ApprovalWorkFlow getApprovalWorkFlowByIdAndApprovalWorkFlowForId(Long formId, Long statusId);

    Set<ApprovalWorkFlow> findAllStatusByFormCode(String formCode);

    //    ApprovalWorkFlow getApprovalWorkFlowByCode(String code);
}
