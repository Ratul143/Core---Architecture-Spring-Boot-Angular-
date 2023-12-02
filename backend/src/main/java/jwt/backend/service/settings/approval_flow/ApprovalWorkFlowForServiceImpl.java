package jwt.backend.service.settings.approval_flow;

/**
 * @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.constant.ApprovalFormEnum;
import jwt.backend.dto.approval_flow.ApprovalWorkFlowForDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowForRepository;
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
public class ApprovalWorkFlowForServiceImpl implements ApprovalWorkFlowForService {
    private final ApprovalWorkFlowForRepository approvalWorkFlowForRepository;
    private final AuthService authService;
    private final CommonService commonService;

    @Override
    public void createApprovalFlowFor(ApprovalWorkFlowForDto approvalWorkFlowForDto) {
        checkIfFormNameExists(approvalWorkFlowForDto.getFlowFor());
        checkIfFormCodeExists(approvalWorkFlowForDto.getCode());
        ApprovalWorkFlowFor createApprovalWorkFlowFor = new ApprovalWorkFlowFor();
        createApprovalWorkFlowFor.setFlowFor(approvalWorkFlowForDto.getFlowFor());
        createApprovalWorkFlowFor.setCode(approvalWorkFlowForDto.getCode());
        createApprovalWorkFlowFor.setIsActive(true);
        createApprovalWorkFlowFor.setCreatedAt(new Date());
        createApprovalWorkFlowFor.setCreatedBy(authService.getAuthUserId());
        createApprovalWorkFlowFor.setUniqueKey(commonService.randomAlphaNumeric());
        approvalWorkFlowForRepository.save(createApprovalWorkFlowFor);
    }

    @Override
    public List<ApprovalWorkFlowFor> findApprovalWorkFlowForPaginate(String approvalWorkFlowFormName, String approvalWorkFlowFormCode, Integer page, Integer size) {
        return approvalWorkFlowForRepository.getApprovalWorkFlowForList(approvalWorkFlowFormName, approvalWorkFlowFormCode, PageRequest.of(page, size));
    }

    @Override
    public void updateApprovalFlowFor(Long id, ApprovalWorkFlowForDto approvalWorkFlowForDto) {
        ApprovalWorkFlowFor updateApprovalWorkFlowFor = findApprovalWorkFlowForById(id);

        if (!Objects.equals(updateApprovalWorkFlowFor.getFlowFor(), approvalWorkFlowForDto.getFlowFor())) {
            checkIfFormNameExists(approvalWorkFlowForDto.getFlowFor());
        }

        if (!Objects.equals(updateApprovalWorkFlowFor.getCode(), approvalWorkFlowForDto.getCode())) {
            checkIfFormCodeExists(approvalWorkFlowForDto.getCode());
        }
        updateApprovalWorkFlowFor.setFlowFor(approvalWorkFlowForDto.getFlowFor());
        updateApprovalWorkFlowFor.setCode(approvalWorkFlowForDto.getCode());
        updateApprovalWorkFlowFor.setUpdatedAt(new Date());
        updateApprovalWorkFlowFor.setUpdatedBy(authService.getAuthUserId());
        approvalWorkFlowForRepository.save(updateApprovalWorkFlowFor);
    }

    @Override
    public void deleteApprovalWorkFlowFor(Long id) {
        ApprovalWorkFlowFor deleteApprovalWorkFlowFor = findApprovalWorkFlowForById(id);
        deleteApprovalWorkFlowFor.setDeletedAt(new Date());
        deleteApprovalWorkFlowFor.setDeletedBy(authService.getAuthUserId());
        approvalWorkFlowForRepository.save(deleteApprovalWorkFlowFor);
    }

    @Override
    public List<ApprovalWorkFlowFor> findAll() {
        return approvalWorkFlowForRepository.findAllByDeletedAtNull();
    }

    @Override
    public ApprovalWorkFlowFor findApprovalWorkFlowForById(Long id) {
        return approvalWorkFlowForRepository.findByIdAndDeletedAtNull(id);
    }

    @Override
    public Long count() {
        return approvalWorkFlowForRepository.countAllByDeletedAtIsNull();
    }

    @Override
    public ApprovalWorkFlowFor findApprovalWorkFlowForByCode(String formCode) {
        return approvalWorkFlowForRepository.findByCodeAndDeletedAtNull(formCode);
    }

    @Override
    public String findFormCodeByFormCode(String formCode) {
        if (formCode.equals("0")) {
            return ApprovalFormEnum.EXAMPLE_FORM.getForm();
        } else {
            return null;
        }
    }

    @SneakyThrows
    public void checkIfFormNameExists(String formName) {
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForRepository.findByFlowForAndDeletedAtNull(formName);
        if (approvalWorkFlowFor != null) {
            throw new DuplicateFoundException("Form exists!");
        }
    }

    @SneakyThrows
    public void checkIfFormCodeExists(String formCode) {
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForRepository.findByCodeAndDeletedAtNull(formCode);
        if (approvalWorkFlowFor != null) {
            throw new DuplicateFoundException("Form code exists!");
        }
    }
}
