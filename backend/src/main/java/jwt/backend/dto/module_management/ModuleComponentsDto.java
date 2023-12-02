package jwt.backend.dto.module_management;

import jwt.backend.entity.module_management.Component;
import jwt.backend.entity.module_management.Module;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleComponentsDto {
    private Module module;
    List<Component> components;
}
