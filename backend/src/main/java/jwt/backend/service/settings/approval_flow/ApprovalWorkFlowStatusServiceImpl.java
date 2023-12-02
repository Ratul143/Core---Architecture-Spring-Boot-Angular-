package jwt.backend.service.settings.approval_flow;

/*
  @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.dto.approval_flow.ApprovalWorkFlowStatusDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowStatusRepository;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalWorkFlowStatusServiceImpl implements ApprovalWorkFlowStatusService {
    private final ApprovalWorkFlowStatusRepository approvalWorkFlowStatusRepository;
    private final AuthService authService;
    private final CommonService commonService;

    @Override
    public void createApprovalFlowStatus(ApprovalWorkFlowStatusDto approvalWorkFlowStatusDto) {
        checkIfStatusIsExists(approvalWorkFlowStatusDto.getStatusName());
        checkIfCodeIsExists(approvalWorkFlowStatusDto.getCode());
        ApprovalWorkFlowStatus createApprovalWorkFlowStatus = new ApprovalWorkFlowStatus();
        createApprovalWorkFlowStatus.setStatusName(approvalWorkFlowStatusDto.getStatusName());
        createApprovalWorkFlowStatus.setCode(approvalWorkFlowStatusDto.getCode());
        createApprovalWorkFlowStatus.setIsActive(true);
        createApprovalWorkFlowStatus.setCreatedAt(new Date());
        createApprovalWorkFlowStatus.setCreatedBy(authService.getAuthUserId());
        createApprovalWorkFlowStatus.setUniqueKey(commonService.randomAlphaNumeric());
        approvalWorkFlowStatusRepository.save(createApprovalWorkFlowStatus);
    }

    @Override
    public void updateApprovalFlowStatus(Long statusId, ApprovalWorkFlowStatusDto approvalWorkFlowStatusDto) {
        ApprovalWorkFlowStatus updateApprovalWorkFlowStatus = findApprovalWorkFlowStatusById(statusId);
        if (!Objects.equals(updateApprovalWorkFlowStatus.getStatusName(), approvalWorkFlowStatusDto.getStatusName())) {
            checkIfStatusIsExists(approvalWorkFlowStatusDto.getStatusName());
        }
        if (!Objects.equals(updateApprovalWorkFlowStatus.getCode(), approvalWorkFlowStatusDto.getCode())) {
            checkIfCodeIsExists(approvalWorkFlowStatusDto.getCode());
        }
        updateApprovalWorkFlowStatus.setStatusName(approvalWorkFlowStatusDto.getStatusName());
        updateApprovalWorkFlowStatus.setCode(approvalWorkFlowStatusDto.getCode());
        updateApprovalWorkFlowStatus.setUpdatedAt(new Date());
        updateApprovalWorkFlowStatus.setUpdatedBy(authService.getAuthUserId());
        approvalWorkFlowStatusRepository.save(updateApprovalWorkFlowStatus);
    }

    @Override
    public List<ApprovalWorkFlowStatus> findApprovalWorkFlowStatusPaginate(String statusName, String statusCode, Integer page, Integer size) {
        return approvalWorkFlowStatusRepository.findApprovalWorkFlowStatusList(statusName, statusCode, PageRequest.of(page, size));
    }

    @Override
    public void deleteApprovalWorkFlowStatus(Long id) {
        ApprovalWorkFlowStatus deleteApprovalWorkFlowStatus = findApprovalWorkFlowStatusById(id);
        deleteApprovalWorkFlowStatus.setDeletedAt(new Date());
        deleteApprovalWorkFlowStatus.setDeletedBy(authService.getAuthUserId());
        approvalWorkFlowStatusRepository.save(deleteApprovalWorkFlowStatus);
    }

    @Override
    public List<ApprovalWorkFlowStatus> findAll() {
        return approvalWorkFlowStatusRepository.findAllByDeletedAtNull();
    }

    @Override
    public ApprovalWorkFlowStatus findApprovalWorkFlowStatusById(Long id) {
        return approvalWorkFlowStatusRepository.findByIdAndDeletedAtNull(id);
    }

    @Override
    public Long count() {
        return approvalWorkFlowStatusRepository.countAllByDeletedAtIsNull();
    }

    @Override
    public ApprovalWorkFlowStatus findApprovalWorkFlowStatusByCode(String code) {
        return approvalWorkFlowStatusRepository.findByCodeAndDeletedAtNull(code);
    }

    @SneakyThrows
    public void checkIfStatusIsExists(String name) {
        ApprovalWorkFlowStatus approvalWorkFlowStatus = approvalWorkFlowStatusRepository.findByStatusNameAndDeletedAtNull(name);
        if (approvalWorkFlowStatus != null) {
            throw new DuplicateFoundException("Status exists!");
        }
    }

    @SneakyThrows
    public void checkIfCodeIsExists(String code) {
        ApprovalWorkFlowStatus approvalWorkFlowSteps = approvalWorkFlowStatusRepository.findByCodeAndDeletedAtNull(code);
        if (approvalWorkFlowSteps != null) {
            throw new DuplicateFoundException("Code exists!");
        }
    }
}
