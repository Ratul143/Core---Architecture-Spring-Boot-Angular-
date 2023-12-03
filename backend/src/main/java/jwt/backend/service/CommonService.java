package jwt.backend.service;

import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.entity.user_management.RecentVisitors;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;

import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface CommonService {
    Long findStatusIdByStatusCode(String code);

    ApprovalWorkFlowStatus findDraftStatus();

    String randomAlphaNumeric();

    RecentVisitors recentVisitors(HttpServletRequest request, Accs_Auth_User user, String visitType);

    List<RecentVisitors> findVisitorsList(String search, Integer page, Integer size);

    Long visitorsCount();

    Date getCurrentDateTime();

    <T> long getCountWithPredicate(Predicate predicate, Class<T> entityClass);

    String formatToTitleCase(String input);

    String formatBoolean(Boolean value);

    Double roundToNDecimalPlaces(Double value, int n);

    Double ceilNumber(Double value);

    Double transformDoubleNumber(Double value);
}
