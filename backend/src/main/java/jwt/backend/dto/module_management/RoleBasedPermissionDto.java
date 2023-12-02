package jwt.backend.dto.module_management;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleBasedPermissionDto {
    private String uniqueKey;
    private String moduleUniqueKey;
    private String subModuleUniqueKey;
    private String componentUniqueKey;
    private Boolean create;
    private Boolean read;
    private Boolean update;
    private Boolean delete;
}
