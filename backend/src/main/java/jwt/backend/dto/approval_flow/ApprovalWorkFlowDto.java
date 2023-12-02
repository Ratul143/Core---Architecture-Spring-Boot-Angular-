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
public class ApprovalWorkFlowDto {
    private Long flowFor;
    private Long status;
    private String statusLevel;
    private Boolean isFinalLevel;
    private Boolean isActive;
}
