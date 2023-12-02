package jwt.backend.service;

/*
  @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.dto.GlobalApproveDeclineDto;
import jwt.backend.entity.GlobalApprovalLog;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.repository.GlobalApprovalLogRepository;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowStatusRepository;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowStatusService;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GlobalLogServiceImpl implements GlobalLogService {
    private final ApprovalWorkFlowStatusRepository approvalWorkFlowStatusRepository;
    private final AuthService authService;
    private final GlobalApprovalLogRepository globalApprovalLogRepository;
    private final CommonService commonService;
    private final ApprovalWorkFlowStatusService approvalWorkFlowStatusService;

    @Override
    public void globalApprovalLog(GlobalApproveDeclineDto globalApproveDeclineDto) {
        ApprovalWorkFlowStatus statusId = approvalWorkFlowStatusService.findApprovalWorkFlowStatusByCode(globalApproveDeclineDto.getNextStatusCode() != null ? globalApproveDeclineDto.getNextStatusCode() : globalApproveDeclineDto.getPreviousStatusCode());
        GlobalApprovalLog globalApprovalLog = new GlobalApprovalLog();
        globalApprovalLog.setUniqueKey(commonService.randomAlphaNumeric());
        globalApprovalLog.setFormName(globalApproveDeclineDto.getFormName());
        globalApprovalLog.setFormCode(globalApproveDeclineDto.getFormCode());
        globalApprovalLog.setFormUniqueId(globalApproveDeclineDto.getUniqueKey());
        globalApprovalLog.setApprovalWorkFlowStatus(statusId);
        globalApprovalLog.setRemarks(globalApproveDeclineDto.getRemarks());
        globalApprovalLog.setIsBacked(globalApproveDeclineDto.getIsBacked());
        globalApprovalLog.setProcessBy(authService.getAuthUser());
        globalApprovalLog.setProcessedAt(commonService.getCurrentDateTime());
        globalApprovalLogRepository.save(globalApprovalLog);
    }

    @Override
    public List<GlobalApprovalLog> findAll(String formUniqueId) {
        return globalApprovalLogRepository.findAllByFormUniqueIdOrderByIdDesc(formUniqueId);
    }
}
