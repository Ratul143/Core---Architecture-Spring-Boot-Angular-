package jwt.backend.entity.settings.approval_flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "APPROVAL_WORK_FLOW_LOG")
@SequenceGenerator(name = "approval_work_flow_log_seq", sequenceName = "APPROVAL_WORK_FLOW_LOG_SEQ", allocationSize = 1)
public class ApprovalWorkFlowLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_work_flow_log_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "UNIQUE_ID", nullable = false)
    private String uniqueId;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "FORM_ID")
    private ApprovalWorkFlowFor approvalWorkFlowFor;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private ApprovalWorkFlowStatus approvalWorkFlowStatus;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "IS_BACKED")
    private Boolean isBacked;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PROCESS_BY")
    private Accs_Auth_User processBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "PROCESSED_AT")
    private Date processedAt;
}
