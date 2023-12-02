package jwt.backend.dto.response;

import jwt.backend.entity.user_management.Role;
import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String token;
    private String type = "Bearer";
    private Role role;

    public LoginResponse(Long id, String username, String fullName, String email, String accessToken, Role role) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.token = accessToken;
        this.role = role;
    }
}
