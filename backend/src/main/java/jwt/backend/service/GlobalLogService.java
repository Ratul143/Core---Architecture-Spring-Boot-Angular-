package jwt.backend.service;

import jwt.backend.dto.GlobalApproveDeclineDto;
import jwt.backend.entity.GlobalApprovalLog;

import java.util.List;

public interface GlobalLogService {
    void globalApprovalLog(GlobalApproveDeclineDto globalApproveDeclineDto);

    List<GlobalApprovalLog> findAll(String formUniqueId);
}
