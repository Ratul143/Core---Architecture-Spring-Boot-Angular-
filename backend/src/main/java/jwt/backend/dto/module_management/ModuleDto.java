package jwt.backend.dto.module_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {
    private String module;
    private String icon;
    private Boolean isActive;
    private Long sortOrder;
}
