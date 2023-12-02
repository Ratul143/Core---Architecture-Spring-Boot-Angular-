package jwt.backend.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String cellNo;
    private Long employeeId;
    private Long enterpriseId;
    private String itemCategoryId;
    private String signature;
    private String countryName;
    private long userTypeId;
    private Long customerId;
    private boolean enabled;
    private boolean accountLocked;
    private long roleId;
}