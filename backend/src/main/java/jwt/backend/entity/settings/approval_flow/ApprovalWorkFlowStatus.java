package jwt.backend.entity.settings.approval_flow;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jwt.backend.entity.BaseEntity;
import jwt.backend.entity.GlobalApprovalLog;
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
@Table(name = "APPROVAL_WORK_FLOW_STATUS")
@SequenceGenerator(name = "approval_work_flow_status_seq", sequenceName = "APPROVAL_WORK_FLOW_STATUS_SEQ", allocationSize = 1)
public class ApprovalWorkFlowStatus extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_work_flow_status_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "STATUS_NAME")
    private String statusName;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CODE")
    private String code;

    @JsonBackReference
    @OneToMany(mappedBy = "approvalWorkFlowStatus", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlow> approvalWorkFlows;

    @Column(name = "IS_BACKED")
    private Boolean isBacked;

    @Column(name = "REMARKS")
    private String remarks;

    @JsonBackReference
    @OneToMany(mappedBy = "nextWorkFlowStatus", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlowSteps> nextWorkFlowStatus;

    @JsonBackReference
    @OneToMany(mappedBy = "currentWorkFlowStatus", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlowSteps> currentWorkFlowStatus;

    @JsonBackReference
    @OneToMany(mappedBy = "approvalWorkFlowStatus", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlowLog> approvalWorkFlowLogs;

    @JsonBackReference
    @OneToMany(mappedBy = "approvalWorkFlowStatus", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<GlobalApprovalLog> globalApprovalLogs;

}
