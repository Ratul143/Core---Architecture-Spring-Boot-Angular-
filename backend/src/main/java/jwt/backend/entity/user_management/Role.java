package jwt.backend.entity.user_management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jwt.backend.entity.BaseEntity;
import jwt.backend.entity.module_management.RoleBasedPermission;
import jwt.backend.entity.module_management.UserBasedPermission;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
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
@Table(name = "ROLES")
@SequenceGenerator(name = "roles_seq", sequenceName = "ROLES_SEQ", allocationSize = 1)
public class Role extends BaseEntity {
    @JsonBackReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Accs_Auth_User> users;
    @JsonBackReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<RoleBasedPermission> roleBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<UserBasedPermission> userBasedPermissions;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "AUTHORITIES")
    private String[] authorities;
}
