package jwt.backend.repository.settings.approval_flow;

import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalWorkFlowStatusRepository extends JpaRepository<ApprovalWorkFlowStatus, Long> {
    List<ApprovalWorkFlowStatus> findAllByDeletedAtNull();

    @Query("select awfs from ApprovalWorkFlowStatus awfs where awfs.deletedAt is null and (lower(awfs.statusName) like %:statusName%) and (lower(awfs.code) like %:statusCode%) order by awfs.id asc")
    List<ApprovalWorkFlowStatus> findApprovalWorkFlowStatusList(@Param("statusName") String statusName, @Param("statusCode") String statusCode, PageRequest pageable);

    ApprovalWorkFlowStatus findByIdAndDeletedAtNull(Long id);

    Long countAllByDeletedAtIsNull();

    ApprovalWorkFlowStatus findByStatusNameAndDeletedAtNull(String name);
    ApprovalWorkFlowStatus findByCodeAndDeletedAtNull(String code);

}