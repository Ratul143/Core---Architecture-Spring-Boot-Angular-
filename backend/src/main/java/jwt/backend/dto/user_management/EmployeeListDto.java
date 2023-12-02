package jwt.backend.dto.user_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListDto {
    private Long employeeId;
    private String autoFormattedId;
    private String employeeName;
}
