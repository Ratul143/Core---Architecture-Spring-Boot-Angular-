package jwt.backend.controller.settings.approval_flow;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.approval_flow.UserFormApprovalFlowDto;
import jwt.backend.service.settings.approval_flow.UserFormApprovalFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.USER_FORM_APPROVAL_FLOW)
@RequiredArgsConstructor
public class UserFormApprovalFlowController {
    private final UserFormApprovalFlowService approvalWorkFlowStepsService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody UserFormApprovalFlowDto approvalWorkFlowStepsDto) {
        approvalWorkFlowStepsService.createUserFormApprovalFlow(approvalWorkFlowStepsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(approvalWorkFlowStepsService.findAll(), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("userFormApprovalFlowId") Long userFormApprovalFlowId, @RequestBody UserFormApprovalFlowDto userFormApprovalFlowDto) {
        approvalWorkFlowStepsService.updateUserFormApprovalFlow(userFormApprovalFlowId, userFormApprovalFlowDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("userFormApprovalFlowId") Long userFormApprovalFlowId) {
        approvalWorkFlowStepsService.deleteUserFormApprovalFlow(userFormApprovalFlowId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(approvalWorkFlowStepsService.count(), HttpStatus.OK);
    }

//    @GetMapping(ApiUrl.LIST)
//    public ResponseEntity<?> list(@RequestParam("formName") String formName, @RequestParam("user") String user, @RequestParam("status") String status, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
//        page = page < 0 ? 0 : page - 1;
//        size = size <= 0 ? 10 : size;
//        List<UserFormApprovalFlow> approvalWorkFlowStepsList = approvalWorkFlowStepsService.findUserFormApprovalFlowPaginate(formName.toLowerCase(), user.toLowerCase(), status.toLowerCase(), page, size);
//        return new ResponseEntity<>(approvalWorkFlowStepsList, HttpStatus.OK);
//    }

    @GetMapping(ApiUrl.GET_BY_FORM)
    public ResponseEntity<?> getByForm(@RequestParam("formId") Long formId) {
        return new ResponseEntity<>(approvalWorkFlowStepsService.getUserFormApprovalFlowByForm(formId), HttpStatus.OK);
    }
}
