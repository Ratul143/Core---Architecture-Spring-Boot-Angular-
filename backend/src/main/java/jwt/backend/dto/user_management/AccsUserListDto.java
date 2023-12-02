package jwt.backend.dto.user_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccsUserListDto {

    private Long id;
    private String username;
    private String fullName;
    private String email;
//    private String role;
    private Long employeeId;
    private boolean enabled;
    private String cellNo;
    private boolean accountLocked;

}
