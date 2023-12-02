package jwt.backend.dto.module_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasedPermissionDto {
    private Long componentId;
    private Long subModuleId;
    private Long moduleId;
    private Boolean create;
    private Boolean read;
    private Boolean update;
    private Boolean delete;
}
