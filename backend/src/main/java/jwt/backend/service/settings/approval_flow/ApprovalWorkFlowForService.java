package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.ApprovalWorkFlowForDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;

import java.util.List;

public interface ApprovalWorkFlowForService {
    void createApprovalFlowFor(ApprovalWorkFlowForDto approvalWorkFlowForDto);

    List<ApprovalWorkFlowFor> findApprovalWorkFlowForPaginate(String searchValueForForm, String searchValueForStatus, Integer page, Integer size);

    void updateApprovalFlowFor(Long formId, ApprovalWorkFlowForDto approvalWorkFlowForDto);

    void deleteApprovalWorkFlowFor(Long id);

    List<ApprovalWorkFlowFor> findAll();

    ApprovalWorkFlowFor findApprovalWorkFlowForById(Long id);

    Long count();

    ApprovalWorkFlowFor findApprovalWorkFlowForByCode(String code);

    String findFormCodeByFormCode(String formCode);
}
