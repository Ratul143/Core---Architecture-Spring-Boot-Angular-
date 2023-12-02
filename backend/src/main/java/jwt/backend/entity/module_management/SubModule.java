package jwt.backend.entity.module_management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jwt.backend.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "SUB_MODULES")
@SequenceGenerator(name = "sub_modules_seq", sequenceName = "SUB_MODULES_SEQ", allocationSize = 1)
public class SubModule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_modules_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    @OneToMany(mappedBy = "subModule", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<RoleBasedPermission> roleBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "subModule", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<UserBasedPermission> userBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "subModule", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    List<Component> components;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "uniqueKey", "moduleName", "icon"})
    @JoinColumn(name = "MODULE_ID")
    private Module module;
    @Column(name = "SUB_MODULE_NAME")
    private String subModuleName;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "SORT_ORDER")
    private Long sortOrder;
}
