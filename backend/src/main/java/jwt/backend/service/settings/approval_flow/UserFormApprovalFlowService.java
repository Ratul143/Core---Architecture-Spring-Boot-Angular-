package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.UserFormApprovalFlowDto;
import jwt.backend.entity.settings.approval_flow.UserFormApprovalFlow;

import java.util.List;

public interface UserFormApprovalFlowService {
    void createUserFormApprovalFlow(UserFormApprovalFlowDto approvalWorkFlowStepsDto);
//    List<UserFormApprovalFlow> findUserFormApprovalFlowPaginate(String formName, String user, String status, Integer page, Integer size);

    void updateUserFormApprovalFlow(Long userFormApprovalFlowId, UserFormApprovalFlowDto userFormApprovalFlowDto);

    void deleteUserFormApprovalFlow(Long userFormApprovalFlowId);

    List<UserFormApprovalFlow> findAll();

    UserFormApprovalFlow findUserFormApprovalFlowById(Long id);

    Long count();

    List<UserFormApprovalFlow> getUserFormApprovalFlowByForm(Long id);

    List<UserFormApprovalFlow> findAllByApprovalWorkFlowForIdAndUserIdAndDeletedAtNull(Long formId, Long userId);
}
