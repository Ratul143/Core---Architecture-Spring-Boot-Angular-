package jwt.backend.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotBlank
    private Long userId;
    private String currentPassword;
    private String newPassword;
}
