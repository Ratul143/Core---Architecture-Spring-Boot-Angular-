package jwt.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "APPROVAL_GLOBAL_LOG")
@SequenceGenerator(name = "approval_global_log_seq", sequenceName = "APPROVAL_GLOBAL_LOG_SEQ", allocationSize = 1)
public class GlobalApprovalLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_global_log_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "FORM_NAME")
    private String formName;

    @Column(name = "FORM_CODE")
    private String formCode;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "IS_BACKED")
    private Boolean isBacked;

    @Column(name = "FORM_UNIQUE_ID")
    private String formUniqueId;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private ApprovalWorkFlowStatus approvalWorkFlowStatus;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_BY")
    private Accs_Auth_User processBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "PROCESSED_AT")
    private Date processedAt;
}
