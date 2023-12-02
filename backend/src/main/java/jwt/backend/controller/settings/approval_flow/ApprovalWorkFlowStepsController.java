package jwt.backend.controller.settings.approval_flow;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.approval_flow.ApprovalWorkFlowStepsDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowSteps;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowForService;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowStepsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.APPROVAL_WORK_FLOW_STEPS)
@RequiredArgsConstructor
public class ApprovalWorkFlowStepsController {
    private final ApprovalWorkFlowStepsService approvalWorkFlowStepsService;
    private final ApprovalWorkFlowForService approvalWorkFlowForService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody ApprovalWorkFlowStepsDto approvalWorkFlowStepsDto) {
        approvalWorkFlowStepsService.createApprovalFlowSteps(approvalWorkFlowStepsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(approvalWorkFlowStepsService.findAll(), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("formId") Long formId, @RequestBody ApprovalWorkFlowSteps approvalWorkFlowSteps) {
        approvalWorkFlowStepsService.updateApprovalFlowSteps(formId, approvalWorkFlowSteps);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("userId") Long userId) {
        approvalWorkFlowStepsService.deleteApprovalWorkFlowSteps(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(approvalWorkFlowStepsService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> list(@RequestParam("formName") String formName, @RequestParam("statusFrom") String statusFrom, @RequestParam("statusTo") String statusTo, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        List<ApprovalWorkFlowSteps> approvalWorkFlowStepsList = approvalWorkFlowStepsService.getApprovalWorkFlowStepsPaginate(formName.toLowerCase(), statusFrom.toLowerCase(), statusTo.toLowerCase(), page, size);
        return new ResponseEntity<>(approvalWorkFlowStepsList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.GET_BY_FORM)
    public ResponseEntity<?> getByForm(@RequestParam("formId") Long formId) {
        return new ResponseEntity<>(approvalWorkFlowStepsService.findApprovalWorkFlowStepsByFormId(formId), HttpStatus.OK);
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
