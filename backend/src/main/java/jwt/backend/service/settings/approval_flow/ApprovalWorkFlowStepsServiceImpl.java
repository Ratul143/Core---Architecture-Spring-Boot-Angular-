package jwt.backend.service.settings.approval_flow;

/**
 * @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.dto.approval_flow.ApprovalWorkFlowStepsDto;
import jwt.backend.dto.approval_flow.properties.ApprovalWorkFlowStepsFieldPropertyDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlow;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowSteps;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowStepsRepository;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalWorkFlowStepsServiceImpl implements ApprovalWorkFlowStepsService {
    private final ApprovalWorkFlowStepsRepository approvalWorkFlowStepsRepository;
    private final ApprovalWorkFlowForService approvalWorkFlowForService;
    private final ApprovalWorkFlowService approvalWorkFlowService;
    private final AuthService authService;
    private final CommonService commonService;

    @Override
    public void createApprovalFlowSteps(ApprovalWorkFlowStepsDto approvalWorkFlowStepsDto) {
        int i = 1;
        for (ApprovalWorkFlowStepsFieldPropertyDto incomingSteps : approvalWorkFlowStepsDto.getSteps()) {
            checkIfStepsAreAssigned(approvalWorkFlowStepsDto.getFormId(), incomingSteps, i);
            ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForById(approvalWorkFlowStepsDto.getFormId());
            ApprovalWorkFlow currentStatus = approvalWorkFlowService.getApprovalWorkFlowByIdAndApprovalWorkFlowForId(incomingSteps.getCurrentWorkFlowId(), approvalWorkFlowStepsDto.getFormId());
            ApprovalWorkFlow nextStatus = approvalWorkFlowService.getApprovalWorkFlowByIdAndApprovalWorkFlowForId(incomingSteps.getNextWorkFlowId(), approvalWorkFlowStepsDto.getFormId());
            ApprovalWorkFlowSteps createApprovalWorkFlowSteps = new ApprovalWorkFlowSteps();
            createApprovalWorkFlowSteps.setApprovalWorkFlowFor(approvalWorkFlowFor);
            createApprovalWorkFlowSteps.setCurrentWorkFlowStatus(currentStatus.getApprovalWorkFlowStatus());
            createApprovalWorkFlowSteps.setNextWorkFlowStatus(nextStatus.getApprovalWorkFlowStatus());
            createApprovalWorkFlowSteps.setIsFinalLevel(incomingSteps.getIsFinal());
            createApprovalWorkFlowSteps.setCreatedAt(new Date());
            createApprovalWorkFlowSteps.setCreatedBy(authService.getAuthUserId());
            createApprovalWorkFlowSteps.setUniqueKey(commonService.randomAlphaNumeric());
            approvalWorkFlowStepsRepository.save(createApprovalWorkFlowSteps);
            i++;
        }
    }

    @Override
    public List<ApprovalWorkFlowSteps> getApprovalWorkFlowStepsPaginate(String formName, String statusFrom, String statusTo, Integer page, Integer size) {
        return approvalWorkFlowStepsRepository.getApprovalWorkFlowStepsList(formName, statusFrom, statusTo, PageRequest.of(page, size));
    }

    @Override
    public void updateApprovalFlowSteps(Long id, ApprovalWorkFlowSteps approvalWorkFlowSteps) {
        ApprovalWorkFlowSteps updateApprovalWorkFlowSteps = getApprovalWorkFlowStepsById(id);
//        updateApprovalWorkFlowSteps.setStepsName(approvalWorkFlowSteps.getStepsName());
//        updateApprovalWorkFlowSteps.setStepsCode(approvalWorkFlowSteps.getStepsCode());
        updateApprovalWorkFlowSteps.setUpdatedAt(new Date());
        updateApprovalWorkFlowSteps.setCreatedBy(authService.getAuthUserId());
        approvalWorkFlowStepsRepository.save(updateApprovalWorkFlowSteps);
    }

    @Override
    public void deleteApprovalWorkFlowSteps(Long id) {
        ApprovalWorkFlowSteps deleteApprovalWorkFlowSteps = getApprovalWorkFlowStepsById(id);
        deleteApprovalWorkFlowSteps.setDeletedAt(new Date());
        deleteApprovalWorkFlowSteps.setDeletedBy(authService.getAuthUserId());
        approvalWorkFlowStepsRepository.save(deleteApprovalWorkFlowSteps);
    }


    @Override
    public List<ApprovalWorkFlowSteps> findAll() {
        return approvalWorkFlowStepsRepository.findAllByDeletedAtNull();
    }

    @Override
    public ApprovalWorkFlowSteps getApprovalWorkFlowStepsById(Long id) {
        return approvalWorkFlowStepsRepository.findByIdAndDeletedAtNull(id);
    }

    @Override
    public Long count() {
        return approvalWorkFlowStepsRepository.countAllByDeletedAtIsNull();
    }

    @Override
    public List<ApprovalWorkFlowSteps> findApprovalWorkFlowStepsByFormId(Long id) {
        return approvalWorkFlowStepsRepository.findAllByApprovalWorkFlowForIdAndDeletedAtNull(id);
    }

    @Override
    public ApprovalWorkFlowSteps findByApprovalWorkFlowForIdAndNextWorkFlowStatusId(Long flowForId, Long nextWorkFlowId) {
        return approvalWorkFlowStepsRepository.findByApprovalWorkFlowForIdAndNextWorkFlowStatusIdAndDeletedAtNull(flowForId, nextWorkFlowId);
    }

    @Override
    public ApprovalWorkFlowSteps findByApprovalWorkFlowForIdAndCurrentWorkFlowStatusId(Long flowForId, Long currentWorkFlowId) {
        return approvalWorkFlowStepsRepository.findByApprovalWorkFlowForIdAndCurrentWorkFlowStatusIdAndDeletedAtNull(flowForId, currentWorkFlowId);
    }

    @Override
    public ApprovalWorkFlowSteps findNextStep(Long flowForId, Long statusId) {
        return approvalWorkFlowStepsRepository.findByApprovalWorkFlowForIdAndCurrentWorkFlowStatusIdAndDeletedAtNull(flowForId, statusId);
    }

    @Override
    public ApprovalWorkFlowSteps findPreviousStep(Long flowForId, Long statusId) {
        return approvalWorkFlowStepsRepository.findByApprovalWorkFlowForIdAndNextWorkFlowStatusIdAndDeletedAtNull(flowForId, statusId);
    }

    @SneakyThrows
    public void checkIfStepsAreAssigned(Long formId, ApprovalWorkFlowStepsFieldPropertyDto incomingSteps, int row) {
        ApprovalWorkFlowSteps approvalWorkFlowSteps = approvalWorkFlowStepsRepository.findAllByApprovalWorkFlowForIdAndCurrentWorkFlowStatusIdAndNextWorkFlowStatusIdAndDeletedAtNull(formId, incomingSteps.getCurrentWorkFlowId(), incomingSteps.getNextWorkFlowId());
        if (approvalWorkFlowSteps != null) {
            throw new DuplicateFoundException("[Row: " + row + "] Steps already assigned!");
        }
    }
}
