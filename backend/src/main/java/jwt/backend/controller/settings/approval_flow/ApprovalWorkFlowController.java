package jwt.backend.controller.settings.approval_flow;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.approval_flow.ApprovalWorkFlowDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlow;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.APPROVAL_WORK_FLOW)
@RequiredArgsConstructor
public class ApprovalWorkFlowController {
    private final ApprovalWorkFlowService approvalWorkFlowService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody ApprovalWorkFlowDto approvalWorkFlowDto) {
        approvalWorkFlowService.createApprovalFlowFor(approvalWorkFlowDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(approvalWorkFlowService.findAll(), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("approvalWorkFlowId") Long approvalWorkFlowId, @RequestBody ApprovalWorkFlowDto approvalWorkFlowDto) {
        approvalWorkFlowService.updateApprovalFlowFor(approvalWorkFlowId, approvalWorkFlowDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("approvalWorkFlowId") Long approvalWorkFlowId) {
        approvalWorkFlowService.deleteApprovalWorkFlow(approvalWorkFlowId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(approvalWorkFlowService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> list(@RequestParam("searchValueForForm") String searchValueForForm, @RequestParam("searchValueForStatus") String searchValueForStatus, @RequestParam("searchValueForCode") String searchValueForCode, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        List<ApprovalWorkFlow> approvalWorkFlowList = approvalWorkFlowService.findApprovalWorkFlowPaginate(searchValueForForm.toLowerCase(), searchValueForStatus.toLowerCase(), searchValueForCode.toLowerCase(), page, size);
        return new ResponseEntity<>(approvalWorkFlowList, HttpStatus.OK);
    }

    @GetMapping(ApiUrl.GET_BY_FORM)
    public ResponseEntity<?> findByForm(@RequestParam("formId") Long formId) {
        return new ResponseEntity<>(approvalWorkFlowService.getApprovalWorkFlowStatusByForm(formId), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.FIND_BY_FORM_CODE)
    public ResponseEntity<?> findByFormCode(@RequestParam("formCode") String formCode) {
        return new ResponseEntity<>(approvalWorkFlowService.findAllStatusByFormCode(formCode), HttpStatus.OK);
    }
}
