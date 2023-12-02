package jwt.backend.dto.approval_flow.properties;

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
public class ApprovalWorkFlowStepsFieldPropertyDto {
    private Long currentWorkFlowId;
    private Long nextWorkFlowId;
    private Boolean isFinal;
}
