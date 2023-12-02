package jwt.backend.entity.settings.approval_flow;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jwt.backend.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "APPROVAL_WORK_FLOW_FOR")
@SequenceGenerator(name = "approval_work_flow_for_seq", sequenceName = "APPROVAL_WORK_FLOW_FOR_SEQ", allocationSize = 1)
public class ApprovalWorkFlowFor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_work_flow_for_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "FLOW_FOR")
    private String flowFor;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ApprovalWorkFlowSteps> approvalWorkFlowSteps;

    @JsonBackReference
    @OneToMany(mappedBy = "approvalWorkFlowFor", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<UserFormApprovalFlow> userFormApprovalFlows;

    @JsonBackReference
    @OneToMany(mappedBy = "approvalWorkFlowFor", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlow> approvalWorkFlows;

    @JsonBackReference
    @OneToMany(mappedBy = "approvalWorkFlowFor", cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    List<ApprovalWorkFlowLog> approvalWorkFlowLogs;

    @Column(name = "CODE")
    private String code;
}
