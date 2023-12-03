package jwt.backend.entity.settings.approval_flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jwt.backend.entity.BaseEntity;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "USER_FORM_APPROVAL_FLOW")
@SequenceGenerator(name = "user_form_approval_flow_seq", sequenceName = "USER_FORM_APPROVAL_FLOW_SEQ", allocationSize = 1)
public class UserFormApprovalFlow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_form_approval_flow_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "FORM_ID")
    private ApprovalWorkFlowFor approvalWorkFlowFor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "USER_ID")
    private Accs_Auth_User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "APPROVAL_WORK_FLOW_ID")
    private ApprovalWorkFlow approvalWorkFlow;
}
