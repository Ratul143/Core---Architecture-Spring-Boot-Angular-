package jwt.backend.entity.user_management.accs_auth;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jwt.backend.entity.module_management.UserBasedPermission;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowLog;
import jwt.backend.entity.settings.approval_flow.UserFormApprovalFlow;
import jwt.backend.entity.user_management.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCS_AUTH_USER")
@SequenceGenerator(name = "auth_user_sequence", sequenceName = "AUTH_USER_SEQ", allocationSize = 1)
public class Accs_Auth_User {

    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<UserBasedPermission> userBasedPermissions;
    @JsonBackReference
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<UserFormApprovalFlow> userFormApprovalFlows;
    @JsonBackReference
    @OneToMany(mappedBy = "processBy", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlowLog> approvalWorkFlowLogs;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_user_sequence")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Email
    @Column(name = "EMAIL")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "CELL_NO")
    private String cellNo;
    @Column(name = "EMPLOYEE_ID", nullable = true)
    private Long employeeId;
    @Column(name = "SIGNATURE")
    private String signature;
    @Column(name = "COUNTRY_NAME")
    private String countryName;
    @Column(name = "IS_DELETED", nullable = true)
    private Boolean isDeleted = false;
    @Column(name = "ENABLED")
    private boolean enabled;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATED")
    private Date createdAt;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    @Column(name = "MODIFIED")
    private Date modifiedAt;
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
    @Column(name = "DELETED_BY_ID", nullable = true)
    private Long deletedById;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "DELETED_DATE")
    private Date deletedAt;
    @Column(name = "ACCOUNT_EXPIRED")
    private boolean accountExpired;
    @Column(name = "ACCOUNT_LOCKED")
    private boolean accountLocked;
    @Column(name = "PASSWORD_EXPIRED")
    private boolean passwordExpired;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "ROLE_ID")
    private Role role;

}
