package jwt.backend.repository.settings.approval_flow;

import jwt.backend.entity.settings.approval_flow.UserFormApprovalFlow;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFormApprovalFlowRepository extends JpaRepository<UserFormApprovalFlow, Long> {
    List<UserFormApprovalFlow> findAllByDeletedAtNull();

//    @Query("select ufaf from UserFormApprovalFlow ufaf where ufaf.deletedAt is null and ((lower(ufaf.approvalWorkFlowFor.flowFor) like %:formName%) and ((lower(ufaf.user.firstName) like %:user%) or (lower(ufaf.user.lastName) like %:user%)) and (lower(ufaf.approvalWorkFlow.approvalWorkFlowStatus.statusName) like %:status%)) order by ufaf.id desc")
//    List<UserFormApprovalFlow> findUserFormApprovalFlowList(@Param("formName") String formName, @Param("user") String user, @Param("status") String status, PageRequest pageable);

    UserFormApprovalFlow findByIdAndDeletedAtNull(Long id);

    Long countAllByDeletedAtIsNull();

    List<UserFormApprovalFlow> findAllByApprovalWorkFlowForIdAndDeletedAtNull(Long formId);

    List<UserFormApprovalFlow> findAllByApprovalWorkFlowForIdAndUserIdAndDeletedAtNull(Long formId, Long userId);
    UserFormApprovalFlow findAllByApprovalWorkFlowForIdAndApprovalWorkFlowApprovalWorkFlowStatusIdAndUserIdAndDeletedAtNull(Long formId, Long statusId, Long userId);

}