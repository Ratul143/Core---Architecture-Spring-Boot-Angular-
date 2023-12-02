package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.ApprovalWorkFlowStatusDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;

import java.util.List;

public interface ApprovalWorkFlowStatusService {
    void createApprovalFlowStatus(ApprovalWorkFlowStatusDto approvalWorkFlowStatusDto);

    List<ApprovalWorkFlowStatus> findApprovalWorkFlowStatusPaginate(String statusName, String statusCode, Integer page, Integer size);

    void updateApprovalFlowStatus(Long statusId, ApprovalWorkFlowStatusDto approvalWorkFlowStatusDto);

    void deleteApprovalWorkFlowStatus(Long id);

    List<ApprovalWorkFlowStatus> findAll();

    ApprovalWorkFlowStatus findApprovalWorkFlowStatusById(Long id);

    Long count();

    ApprovalWorkFlowStatus findApprovalWorkFlowStatusByCode(String code);
}
