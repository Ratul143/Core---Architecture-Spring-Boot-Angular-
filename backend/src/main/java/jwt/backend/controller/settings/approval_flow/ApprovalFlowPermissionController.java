package jwt.backend.controller.settings.approval_flow;

import jwt.backend.constant.ApiUrl;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.service.settings.approval_flow.ApprovalFlowPermissionService;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowForService;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowStepsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.APPROVAL_WORK_FLOW_PERMISSION)
@RequiredArgsConstructor
public class ApprovalFlowPermissionController {
    private final ApprovalFlowPermissionService approvalFlowPermissionService;
    private final ApprovalWorkFlowForService approvalWorkFlowForService;
    private final ApprovalWorkFlowStepsService approvalWorkFlowStepsService;

    @GetMapping(ApiUrl.LOG_LIST)
    public ResponseEntity<?> logList(@RequestParam("uniqueRequisitionId") String uniqueId) {
        return new ResponseEntity<>(approvalFlowPermissionService.logList(uniqueId), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.GET_NEXT_STEP)
    public ResponseEntity<?> getNextSteps(@RequestParam("formCode") String formCode, @RequestParam("statusId") Long statusId) {
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForByCode(formCode);
        return new ResponseEntity<>(approvalWorkFlowStepsService.findNextStep(approvalWorkFlowFor.getId(), statusId), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.GET_PREVIOUS_STEP)
    public ResponseEntity<?> getPreviousSteps(@RequestParam("formCode") String formCode, @RequestParam("statusId") Long statusId) {
        ApprovalWorkFlowFor approvalWorkFlowFor = approvalWorkFlowForService.findApprovalWorkFlowForByCode(formCode);
        return new ResponseEntity<>(approvalWorkFlowStepsService.findPreviousStep(approvalWorkFlowFor.getId(), statusId), HttpStatus.OK);
    }


}
