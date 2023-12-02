package jwt.backend.dto.module_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDto {
    private Long module;
    private String subModule; // uniqueKey for sub-module value
    private String component;
    private String icon;
    private String path;
    private Boolean isActive;
    private Long sortOrder;

}
