package jwt.backend.dto.module_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubModuleDto {
    private String module; // uniqueKey for module value
    private String subModuleName;
    private String icon;
    private Boolean isActive;
    private Long sortOrder;
}
