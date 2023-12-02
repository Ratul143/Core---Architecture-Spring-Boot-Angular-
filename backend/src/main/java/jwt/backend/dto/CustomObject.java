package jwt.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jwt.backend.entity.module_management.Component;
import jwt.backend.entity.module_management.Module;
import jwt.backend.entity.module_management.SubModule;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CustomObject {
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "qriusErpComponent", "qriusErpSubModule"})
    Set<Module> modules = new HashSet<>();
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<SubModule> subModules = new HashSet<>();
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<Component> components = new HashSet<>();
}
