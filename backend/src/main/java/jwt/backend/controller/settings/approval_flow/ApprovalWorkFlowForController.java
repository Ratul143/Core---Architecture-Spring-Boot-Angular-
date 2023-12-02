package jwt.backend.controller.settings.approval_flow;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.approval_flow.ApprovalWorkFlowForDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowFor;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowForService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.APPROVAL_WORK_FLOW_FOR)
@RequiredArgsConstructor
public class ApprovalWorkFlowForController {
    private final ApprovalWorkFlowForService approvalWorkFlowForService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody ApprovalWorkFlowForDto approvalWorkFlowForDto) {
        approvalWorkFlowForService.createApprovalFlowFor(approvalWorkFlowForDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(approvalWorkFlowForService.findAll(), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("formId") Long formId, @RequestBody ApprovalWorkFlowForDto approvalWorkFlowForDto) {
        approvalWorkFlowForService.updateApprovalFlowFor(formId, approvalWorkFlowForDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("userId") Long userId) {
        approvalWorkFlowForService.deleteApprovalWorkFlowFor(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(approvalWorkFlowForService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> list(@RequestParam("approvalWorkFlowFormName") String approvalWorkFlowFormName, @RequestParam("approvalWorkFlowFormCode") String approvalWorkFlowFormCode, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        List<ApprovalWorkFlowFor> approvalWorkFlowForList = approvalWorkFlowForService.findApprovalWorkFlowForPaginate(approvalWorkFlowFormName.toLowerCase(), approvalWorkFlowFormCode.toLowerCase(), page, size);
        return new ResponseEntity<>(approvalWorkFlowForList, HttpStatus.OK);
    }
}
