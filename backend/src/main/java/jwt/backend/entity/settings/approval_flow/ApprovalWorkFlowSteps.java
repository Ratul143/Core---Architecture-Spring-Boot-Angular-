package jwt.backend.entity.settings.approval_flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jwt.backend.entity.BaseEntity;
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
@Table(name = "APPROVAL_WORK_FLOW_STEPS")
@SequenceGenerator(name = "approval_work_flow_steps_seq", sequenceName = "APPROVAL_WORK_FLOW_STEPS_SEQ", allocationSize = 1)
public class ApprovalWorkFlowSteps extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_work_flow_steps_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "APPROVAL_WORK_FLOW_FOR_ID")
    private ApprovalWorkFlowFor approvalWorkFlowFor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "CURRENT_WORK_FLOW_STATUS_ID")
    private ApprovalWorkFlowStatus currentWorkFlowStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "NEXT_WORK_FLOW_STATUS_ID")
    private ApprovalWorkFlowStatus nextWorkFlowStatus;

    @Column(name = "IS_FINAL_LEVEL")
    private Boolean isFinalLevel;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
}
