package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.ApproveDeclineDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowLog;
import jwt.backend.exception.approval.FormNotFoundException;
import jwt.backend.exception.user_management.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface ApprovalFlowPermissionService {
    Set<Long> permissionList(String formName) throws FormNotFoundException, UserNotFoundException;

    ApprovalWorkFlowLog approvalFlowLog(String formCode, ApproveDeclineDto approveDeclineDto);

    List<ApprovalWorkFlowLog> logList(String uniqueId);

}
