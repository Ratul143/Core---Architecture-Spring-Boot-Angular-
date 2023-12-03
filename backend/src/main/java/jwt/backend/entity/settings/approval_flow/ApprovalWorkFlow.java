package jwt.backend.entity.settings.approval_flow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jwt.backend.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

/**
 * @author Jaber
 * @date 7/28/2022
 * @time 9:52 AM
 */
@Entity
@Getter
@Setter
@Table(name = "APPROVAL_WORK_FLOW")
@SequenceGenerator(name = "approval_work_flow_seq", sequenceName = "APPROVAL_WORK_FLOW_SEQ", allocationSize = 1)
public class ApprovalWorkFlow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_work_flow_seq")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "STATUS_LEVEL")
    private String statusLevel;

    @Column(name = "IS_FINAL_LEVEL")
    private Boolean isFinalLevel;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVAL_WORK_FLOW_FOR_ID")
    private ApprovalWorkFlowFor approvalWorkFlowFor;
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private ApprovalWorkFlowStatus approvalWorkFlowStatus;

    @OneToMany(mappedBy = "approvalWorkFlow", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserFormApprovalFlow> userFormApprovalFlows;
}
