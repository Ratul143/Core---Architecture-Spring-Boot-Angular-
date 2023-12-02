package jwt.backend.dto.approval_flow;

import lombok.*;

/**
 * @author Jaber
 * @date 7/21/2022
 * @time 11:41 AM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveDeclineDto {
    private Long statusId;
    private String statusCode;
    private String remarks;
    private Boolean isBack;
    private Long nextWorkFlowStatusId;
    private Long previousWorkFlowStatusId;
}
