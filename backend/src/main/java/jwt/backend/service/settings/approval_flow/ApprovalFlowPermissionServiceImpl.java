package jwt.backend.service.settings.approval_flow;

import jwt.backend.dto.approval_flow.ApproveDeclineDto;
import jwt.backend.entity.settings.approval_flow.*;
import jwt.backend.exception.approval.FormNotFoundException;
import jwt.backend.exception.user_management.UserNotFoundException;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowLogRepository;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalFlowPermissionServiceImpl implements ApprovalFlowPermissionService {
    public static final String FORM_NOT_FOUND = "Form not found!";
    public static final String USER_NOT_FOUND = "User not found!";
    private final ApprovalWorkFlowForService approvalWorkFlowForService;
    private final AuthService authService;
    private final ApprovalWorkFlowStepsService approvalWorkFlowStepsService;
    private final UserFormApprovalFlowService userFormApprovalFlowService;
    private final ApprovalWorkFlowStatusService approvalWorkFlowStatusService;
    private final ApprovalWorkFlowLogRepository approvalWorkFlowLogRepository;

    @Override
    public Set<Long> permissionList(String formCode) throws FormNotFoundException, UserNotFoundException {
        ApprovalWorkFlowFor form = approvalWorkFlowForService.findApprovalWorkFlowForByCode(formCode);
        if (form == null) {
            throw new FormNotFoundException(FORM_NOT_FOUND);
        }
        Long loggedInUserId = authService.getAuthUserId();
        if (loggedInUserId == null) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        List<UserFormApprovalFlow> userFormApprovalFlows = userFormApprovalFlowService.findAllByApprovalWorkFlowForIdAndUserIdAndDeletedAtNull(form.getId(), loggedInUserId);
        Set<Long> approvalWorkFlowStatusList = new HashSet<>();
        ApprovalWorkFlowSteps approvalWorkFlowSteps;
        for (UserFormApprovalFlow userFormApprovalFlow : userFormApprovalFlows) {
            if (Long.parseLong(userFormApprovalFlow.getApprovalWorkFlow().getStatusLevel()) > 0) {
                approvalWorkFlowSteps = approvalWorkFlowStepsService.findByApprovalWorkFlowForIdAndNextWorkFlowStatusId(form.getId(), userFormApprovalFlow.getApprovalWorkFlow().getApprovalWorkFlowStatus().getId());
                if (approvalWorkFlowSteps != null) {
                    approvalWorkFlowStatusList.add(approvalWorkFlowSteps.getCurrentWorkFlowStatus().getId());
                }
            } else {
                approvalWorkFlowSteps = approvalWorkFlowStepsService.findByApprovalWorkFlowForIdAndCurrentWorkFlowStatusId(form.getId(), userFormApprovalFlow.getApprovalWorkFlow().getApprovalWorkFlowStatus().getId());
                if (approvalWorkFlowSteps != null) {
                    approvalWorkFlowStatusList.add(approvalWorkFlowSteps.getCurrentWorkFlowStatus().getId());
                }
            }
        }
        return approvalWorkFlowStatusList;
    }

    @Override
    public ApprovalWorkFlowLog approvalFlowLog(String formCode, ApproveDeclineDto approveDeclineDto) {
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForByCode(formCode);
        ApprovalWorkFlowStatus approvalWorkFlowStatus = approvalWorkFlowStatusService.findApprovalWorkFlowStatusById(approveDeclineDto.getNextWorkFlowStatusId() != null ? approveDeclineDto.getNextWorkFlowStatusId() : approveDeclineDto.getPreviousWorkFlowStatusId());
        ApprovalWorkFlowLog approvalWorkFlowLog = new ApprovalWorkFlowLog();
        approvalWorkFlowLog.setUniqueId(RandomStringUtils.randomAlphanumeric(20));
        approvalWorkFlowLog.setApprovalWorkFlowFor(approvalWorkFlowFor);
        approvalWorkFlowLog.setApprovalWorkFlowStatus(approvalWorkFlowStatus);
        approvalWorkFlowLog.setRemarks(approveDeclineDto.getRemarks());
        approvalWorkFlowLog.setIsBacked(approveDeclineDto.getIsBack());
//        approvalWorkFlowLog.setProcessBy(authService.getAuthUser());
        approvalWorkFlowLog.setProcessedAt(new Date());
        return approvalWorkFlowLogRepository.save(approvalWorkFlowLog);
    }

    @Override
    public List<ApprovalWorkFlowLog> logList(String uniqueId) {
        return approvalWorkFlowLogRepository.findAllByUniqueIdOrderByIdDesc(uniqueId);
    }

}
