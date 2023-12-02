package jwt.backend.dto;

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
public class GlobalApproveDeclineDto {
    private String formName;
    private String formCode;
    private String remarks;
    private String uniqueKey;
    private String nextStatusCode;
    private String previousStatusCode;
    private Boolean isBacked;
}
