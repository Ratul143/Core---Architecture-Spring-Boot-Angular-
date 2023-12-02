package jwt.backend.service.settings.approval_flow;

/**
 * @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.dto.approval_flow.UserFormApprovalFlowDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlow;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.entity.settings.approval_flow.UserFormApprovalFlow;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import jwt.backend.exception.DuplicateFoundException;
import jwt.backend.repository.settings.approval_flow.UserFormApprovalFlowRepository;
import jwt.backend.service.CommonService;
import jwt.backend.service.user_management.AuthService;
import jwt.backend.service.user_management.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFormApprovalFlowServiceImpl implements UserFormApprovalFlowService {
    private final UserFormApprovalFlowRepository userFormApprovalFlowRepository;
    private final ApprovalWorkFlowForService approvalWorkFlowForService;
    private final UserService userService;
    private final ApprovalWorkFlowService approvalWorkFlowService;
    private final AuthService authService;
    private final CommonService commonService;

    @Override
    public void createUserFormApprovalFlow(UserFormApprovalFlowDto userFormApprovalFlowDto) {
        checkIfPermissionIsAssigned(userFormApprovalFlowDto);
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForById(userFormApprovalFlowDto.getForm());
        Accs_Auth_User user = authService.findUserById(userFormApprovalFlowDto.getUser()); // newly added
        ApprovalWorkFlow approvalWorkFlow = approvalWorkFlowService.findApprovalWorkFlowById(userFormApprovalFlowDto.getStatus());
        UserFormApprovalFlow createUserFormApprovalFlow = new UserFormApprovalFlow();
        createUserFormApprovalFlow.setApprovalWorkFlowFor(approvalWorkFlowFor);
        createUserFormApprovalFlow.setApprovalWorkFlow(approvalWorkFlow);
        createUserFormApprovalFlow.setUser(user);
        createUserFormApprovalFlow.setCreatedBy(authService.getAuthUserId());
        createUserFormApprovalFlow.setUniqueKey(commonService.randomAlphaNumeric());
        userFormApprovalFlowRepository.save(createUserFormApprovalFlow);
    }

//    @Override
//    public List<UserFormApprovalFlow> findUserFormApprovalFlowPaginate(String formName, String user, String status, Integer page, Integer size) {
//        return userFormApprovalFlowRepository.findUserFormApprovalFlowList(formName, user, status, PageRequest.of(page, size));
//    }

    @Override
    public void updateUserFormApprovalFlow(Long id, UserFormApprovalFlowDto userFormApprovalFlowDto) {
        UserFormApprovalFlow updateUserFormApprovalFlow = findUserFormApprovalFlowById(id);
        if (!Objects.equals(updateUserFormApprovalFlow.getApprovalWorkFlowFor().getId(), userFormApprovalFlowDto.getForm())) {
            checkIfPermissionIsAssigned(userFormApprovalFlowDto);
        }
        if (!Objects.equals(updateUserFormApprovalFlow.getApprovalWorkFlow().getApprovalWorkFlowStatus().getId(), userFormApprovalFlowDto.getStatus())) {
            checkIfPermissionIsAssigned(userFormApprovalFlowDto);
        }
        if (!Objects.equals(updateUserFormApprovalFlow.getUser().getId(), userFormApprovalFlowDto.getUser())) { // newly updated
            checkIfPermissionIsAssigned(userFormApprovalFlowDto);
        }
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForById(userFormApprovalFlowDto.getForm());
        Accs_Auth_User user = authService.findUserById(userFormApprovalFlowDto.getUser()); // newly added
        ApprovalWorkFlow approvalWorkFlow = approvalWorkFlowService.findApprovalWorkFlowById(userFormApprovalFlowDto.getStatus());
        updateUserFormApprovalFlow.setApprovalWorkFlowFor(approvalWorkFlowFor);
        updateUserFormApprovalFlow.setApprovalWorkFlow(approvalWorkFlow);
        updateUserFormApprovalFlow.setUser(user);
        updateUserFormApprovalFlow.setUpdatedAt(new Date());
        updateUserFormApprovalFlow.setUpdatedBy(authService.getAuthUserId());
        userFormApprovalFlowRepository.save(updateUserFormApprovalFlow);
    }

    @Override
    public void deleteUserFormApprovalFlow(Long userFormApprovalFlowId) {
        UserFormApprovalFlow deleteUserFormApprovalFlow = findUserFormApprovalFlowById(userFormApprovalFlowId);
        deleteUserFormApprovalFlow.setDeletedAt(new Date());
        deleteUserFormApprovalFlow.setDeletedBy(authService.getAuthUserId());
        userFormApprovalFlowRepository.save(deleteUserFormApprovalFlow);
    }

    @Override
    public List<UserFormApprovalFlow> findAll() {
        return userFormApprovalFlowRepository.findAllByDeletedAtNull();
    }

    @Override
    public UserFormApprovalFlow findUserFormApprovalFlowById(Long id) {
        return userFormApprovalFlowRepository.findByIdAndDeletedAtNull(id);
    }

    @Override
    public Long count() {
        return userFormApprovalFlowRepository.countAllByDeletedAtIsNull();
    }

    @Override
    public List<UserFormApprovalFlow> getUserFormApprovalFlowByForm(Long id) {
        return userFormApprovalFlowRepository.findAllByApprovalWorkFlowForIdAndDeletedAtNull(id);
    }

    @Override
    public List<UserFormApprovalFlow> findAllByApprovalWorkFlowForIdAndUserIdAndDeletedAtNull(Long formId, Long userId) {
        return userFormApprovalFlowRepository.findAllByApprovalWorkFlowForIdAndUserIdAndDeletedAtNull(formId, userId);
    }

    @SneakyThrows
    public void checkIfPermissionIsAssigned(UserFormApprovalFlowDto userFormApprovalFlowDto) {
        UserFormApprovalFlow userFormApprovalFlow = userFormApprovalFlowRepository.findAllByApprovalWorkFlowForIdAndApprovalWorkFlowApprovalWorkFlowStatusIdAndUserIdAndDeletedAtNull(userFormApprovalFlowDto.getForm(), userFormApprovalFlowDto.getStatus(), userFormApprovalFlowDto.getUser());
        if (userFormApprovalFlow != null) {
            throw new DuplicateFoundException("User already assigned!");
        }
    }
}
