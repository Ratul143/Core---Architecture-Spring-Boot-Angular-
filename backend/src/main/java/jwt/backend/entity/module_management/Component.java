package jwt.backend.entity.module_management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jwt.backend.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "COMPONENTS")
@SequenceGenerator(name = "components_seq", sequenceName = "COMPONENTS_SEQ", allocationSize = 1)
public class Component extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "components_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    @OneToMany(mappedBy = "component", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<RoleBasedPermission> roleBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "component", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<UserBasedPermission> userBasedPermissions;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "uniqueKey", "moduleName"})
    @JoinColumn(name = "MODULE_ID")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "uniqueKey", "subModuleName"})
    @JoinColumn(name = "SUB_MODULE_ID")
    private SubModule subModule;
    @Column(name = "COMPONENT_NAME")
    private String componentName;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "PATH")
    private String path;
    @Column(name = "SORT_ORDER")
    private Long sortOrder;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
}
