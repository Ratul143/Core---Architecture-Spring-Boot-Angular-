package jwt.backend.dto.user_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private String role;
    private String[] authorities;
}
