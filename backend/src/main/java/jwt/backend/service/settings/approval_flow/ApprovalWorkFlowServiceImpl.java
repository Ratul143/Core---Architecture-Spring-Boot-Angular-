package jwt.backend.service.settings.approval_flow;

/**
 * @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.dto.approval_flow.ApprovalWorkFlowDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlow;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowForRepository;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowRepository;
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
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalWorkFlowServiceImpl implements ApprovalWorkFlowService {
    private final ApprovalWorkFlowRepository approvalWorkFlowRepository;
    private final ApprovalWorkFlowForService approvalWorkFlowForService;
    private final ApprovalWorkFlowStatusService approvalWorkFlowStatusService;
    private final AuthService authService;
    private final CommonService commonService;
    private final ApprovalWorkFlowForRepository approvalWorkFlowForRepository;

    @Override
    public void createApprovalFlowFor(ApprovalWorkFlowDto approvalWorkFlowDto) {
        checkIfApprovalFlowExists(approvalWorkFlowDto);
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForById(approvalWorkFlowDto.getFlowFor());
        ApprovalWorkFlowStatus approvalWorkFlowStatus = approvalWorkFlowStatusService.findApprovalWorkFlowStatusById(approvalWorkFlowDto.getStatus());
        ApprovalWorkFlow createApprovalWorkFlow = new ApprovalWorkFlow();
        createApprovalWorkFlow.setApprovalWorkFlowFor(approvalWorkFlowFor);
        createApprovalWorkFlow.setApprovalWorkFlowStatus(approvalWorkFlowStatus);
        createApprovalWorkFlow.setStatusLevel(approvalWorkFlowDto.getStatusLevel());
        createApprovalWorkFlow.setIsFinalLevel(approvalWorkFlowDto.getIsFinalLevel());
        createApprovalWorkFlow.setIsActive(approvalWorkFlowDto.getIsActive());
        createApprovalWorkFlow.setCreatedAt(new Date());
        createApprovalWorkFlow.setCreatedBy(authService.getAuthUserId());
        createApprovalWorkFlow.setUniqueKey(commonService.randomAlphaNumeric());
        approvalWorkFlowRepository.save(createApprovalWorkFlow);
    }

    @Override
    public List<ApprovalWorkFlow> findApprovalWorkFlowPaginate(String approvalWorkFlowName, String approvalWorkFlowStatus, String approvalWorkFlowCode, Integer page, Integer size) {
        return approvalWorkFlowRepository.findApprovalWorkFlowList(approvalWorkFlowName, approvalWorkFlowStatus, approvalWorkFlowCode, PageRequest.of(page, size));
    }

    @Override
    public void updateApprovalFlowFor(Long approvalWorkFlowId, ApprovalWorkFlowDto approvalWorkFlowDto) {
        ApprovalWorkFlow updateApprovalWorkFlow = findApprovalWorkFlowById(approvalWorkFlowId);
        if (!Objects.equals(updateApprovalWorkFlow.getApprovalWorkFlowFor().getId(), approvalWorkFlowDto.getFlowFor())) {
            checkIfApprovalFlowExists(approvalWorkFlowDto);
        }

        if (!Objects.equals(updateApprovalWorkFlow.getApprovalWorkFlowStatus().getId(), approvalWorkFlowDto.getStatus())) {
            checkIfApprovalFlowExists(approvalWorkFlowDto);
        }

        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForById(approvalWorkFlowDto.getFlowFor());
        ApprovalWorkFlowStatus approvalWorkFlowStatus = approvalWorkFlowStatusService.findApprovalWorkFlowStatusById(approvalWorkFlowDto.getStatus());
        updateApprovalWorkFlow.setApprovalWorkFlowFor(approvalWorkFlowFor);
        updateApprovalWorkFlow.setApprovalWorkFlowStatus(approvalWorkFlowStatus);
        updateApprovalWorkFlow.setStatusLevel(approvalWorkFlowDto.getStatusLevel());
        updateApprovalWorkFlow.setIsFinalLevel(approvalWorkFlowDto.getIsFinalLevel());
        updateApprovalWorkFlow.setIsActive(approvalWorkFlowDto.getIsActive());
        updateApprovalWorkFlow.setUpdatedAt(new Date());
        updateApprovalWorkFlow.setUpdatedBy(authService.getAuthUserId());
        approvalWorkFlowRepository.save(updateApprovalWorkFlow);
    }

    @Override
    public void deleteApprovalWorkFlow(Long approvalWorkFlowId) {
        ApprovalWorkFlow deleteApprovalWorkFlow = findApprovalWorkFlowById(approvalWorkFlowId);
        deleteApprovalWorkFlow.setDeletedAt(new Date());
        deleteApprovalWorkFlow.setDeletedBy(authService.getAuthUserId());
        approvalWorkFlowRepository.save(deleteApprovalWorkFlow);
    }

    @Override
    public List<ApprovalWorkFlow> findAll() {
        return approvalWorkFlowRepository.findAllByDeletedAtNull();
    }

    @Override
    public ApprovalWorkFlow findApprovalWorkFlowById(Long id) {
        return approvalWorkFlowRepository.findByIdAndDeletedAtNull(id);
    }

    @Override
    public Long count() {
        return approvalWorkFlowRepository.countAllByDeletedAtIsNull();
    }

    @Override
    public Set<ApprovalWorkFlow> getApprovalWorkFlowStatusByForm(Long id) {
        return approvalWorkFlowRepository.findAllByApprovalWorkFlowForIdAndDeletedAtNull(id);
    }

    @Override
    public ApprovalWorkFlow getApprovalWorkFlowByIdAndApprovalWorkFlowForId(Long id, Long formId) {
        return approvalWorkFlowRepository.findByIdAndApprovalWorkFlowForIdAndDeletedAtNull(id, formId);
    }

    @Override
    public Set<ApprovalWorkFlow> findAllStatusByFormCode(String formCode) {
        return approvalWorkFlowRepository.findAllByApprovalWorkFlowForIdAndDeletedAtNull(approvalWorkFlowForRepository.findByCodeAndDeletedAtNull(formCode).getId());
    }

    @SneakyThrows
    public void checkIfApprovalFlowExists(ApprovalWorkFlowDto approvalWorkFlowDto) {
        ApprovalWorkFlow approvalWorkFlow = approvalWorkFlowRepository.findByApprovalWorkFlowForIdAndApprovalWorkFlowStatusIdAndDeletedAtNull(approvalWorkFlowDto.getFlowFor(), approvalWorkFlowDto.getStatus());
        if (approvalWorkFlow != null) {
            throw new DuplicateFoundException("Approval flow exists!");
        }
    }
}
