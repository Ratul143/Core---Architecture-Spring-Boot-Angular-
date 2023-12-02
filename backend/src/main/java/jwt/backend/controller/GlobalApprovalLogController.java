package jwt.backend.controller;

import jwt.backend.constant.ApiUrl;
import jwt.backend.exception.ExceptionHandling;
import jwt.backend.service.GlobalLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ApiUrl.LOGS)
public class GlobalApprovalLogController extends ExceptionHandling {
    private final GlobalLogService globalLogService;

    @GetMapping(ApiUrl.FIND_BY_FORM_UNIQUE_ID)
    public ResponseEntity<?> findAllByFormUniqueKey(@RequestParam("formUniqueId") String formUniqueId) {
        return new ResponseEntity<>(globalLogService.findAll(formUniqueId), HttpStatus.OK);
    }
}
