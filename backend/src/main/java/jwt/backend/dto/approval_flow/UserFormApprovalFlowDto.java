package jwt.backend.dto.approval_flow;

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
public class UserFormApprovalFlowDto {
    private Long form;
    private Long status;
    private Long user;
}
