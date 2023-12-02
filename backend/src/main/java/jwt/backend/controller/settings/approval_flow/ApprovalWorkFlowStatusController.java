package jwt.backend.controller.settings.approval_flow;

import jwt.backend.constant.ApiUrl;
import jwt.backend.dto.approval_flow.ApprovalWorkFlowStatusDto;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.service.settings.approval_flow.ApprovalWorkFlowStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiUrl.BASE_API + ApiUrl.APPROVAL_WORK_FLOW_STATUS)
@RequiredArgsConstructor
public class ApprovalWorkFlowStatusController {
    private final ApprovalWorkFlowStatusService approvalWorkFlowStatusService;

    @PostMapping(ApiUrl.CREATE)
    public ResponseEntity<?> create(@RequestBody ApprovalWorkFlowStatusDto approvalWorkFlowStatusDto) {
        approvalWorkFlowStatusService.createApprovalFlowStatus(approvalWorkFlowStatusDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ApiUrl.FIND_ALL)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(approvalWorkFlowStatusService.findAll(), HttpStatus.OK);
    }

    @PostMapping(ApiUrl.UPDATE)
    public ResponseEntity<?> update(@RequestParam("statusId") Long statusId, @RequestBody ApprovalWorkFlowStatusDto approvalWorkFlowStatusDto) {
        approvalWorkFlowStatusService.updateApprovalFlowStatus(statusId, approvalWorkFlowStatusDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(ApiUrl.DELETE)
    public ResponseEntity<?> delete(@RequestParam("statusId") Long statusId) {
        approvalWorkFlowStatusService.deleteApprovalWorkFlowStatus(statusId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(ApiUrl.COUNT)
    public ResponseEntity<?> count() {
        return new ResponseEntity<>(approvalWorkFlowStatusService.count(), HttpStatus.OK);
    }

    @GetMapping(ApiUrl.LIST)
    public ResponseEntity<?> list(@RequestParam("statusName") String statusName, @RequestParam("statusCode") String statusCode, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page - 1;
        size = size <= 0 ? 10 : size;
        List<ApprovalWorkFlowStatus> approvalWorkFlowStatusList = approvalWorkFlowStatusService.findApprovalWorkFlowStatusPaginate(statusName.toLowerCase(), statusCode.toLowerCase(), page, size);
        return new ResponseEntity<>(approvalWorkFlowStatusList, HttpStatus.OK);
    }
}
