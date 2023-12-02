package jwt.backend.dto.approval_flow;

import jwt.backend.dto.approval_flow.properties.ApprovalWorkFlowStepsFieldPropertyDto;
import lombok.*;

import java.util.List;

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
public class ApprovalWorkFlowStepsDto {
    private Long formId;
    private List<ApprovalWorkFlowStepsFieldPropertyDto> steps;
}
