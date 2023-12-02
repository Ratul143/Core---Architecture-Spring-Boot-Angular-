package jwt.backend.entity.module_management;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jwt.backend.entity.BaseEntity;
import jwt.backend.entity.user_management.Role;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "USER_BASED_PERMISSIONS")
@SequenceGenerator(name = "user_based_permissions_seq", sequenceName = "USER_BASED_PERMISSIONS_SEQ", allocationSize = 1)
public class UserBasedPermission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_based_permissions_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "USER_ID")
    private Accs_Auth_User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "MODULE_ID")
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "SUB_MODULE_ID")
    private SubModule subModule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "COMPONENT_ID")
    private Component component;

    @Column(name = "CREATE")
    private Boolean create;
    @Column(name = "READ")
    private Boolean read;
    @Column(name = "UPDATE")
    private Boolean update;
    @Column(name = "DELETE")
    private Boolean delete;
}
