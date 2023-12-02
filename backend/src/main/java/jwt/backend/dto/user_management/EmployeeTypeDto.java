package jwt.backend.dto.user_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTypeDto {

    private Long id;
    private String title;
}
