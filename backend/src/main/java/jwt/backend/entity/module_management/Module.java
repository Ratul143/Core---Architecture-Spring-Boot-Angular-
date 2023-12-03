package jwt.backend.entity.module_management;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "MODULES")
@SequenceGenerator(name = "modules_seq", sequenceName = "MODULES_SEQ", allocationSize = 1)
public class Module extends BaseEntity {
    @JsonBackReference
    @OneToMany(mappedBy = "module", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<RoleBasedPermission> roleBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "module", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<UserBasedPermission> userBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "module", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<UserBasedPermission> componentPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "module", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<SubModule> subModules;
    @JsonBackReference
    @OneToMany(mappedBy = "module", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<Component> components;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "modules_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    @Column(name = "MODULE_NAME")
    private String moduleName;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "SORT_ORDER")
    private Long sortOrder;
}
