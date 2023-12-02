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
public class MenuContainer {
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "component", "subModule"})
    Set<Module> modules = new HashSet<>();
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<SubModule> subModules = new HashSet<>();
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Set<Component> components = new HashSet<>();
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "module", "subModule", "createdAt", "updatedAt", "createdBy", "deletedBy"})
    Set<Object> permissions = new HashSet<>();
}
