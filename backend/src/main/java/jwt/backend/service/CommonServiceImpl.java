package jwt.backend.service;

/**
 * @author Jaber
 * @date 10/09/2022
 * @time 09:59 AM
 */

import jwt.backend.constant.ApprovalStatusEnum;
import jwt.backend.constant.CustomMessage;
import jwt.backend.entity.settings.approval_flow.ApprovalWorkFlowStatus;
import jwt.backend.entity.user_management.RecentVisitors;
import jwt.backend.entity.user_management.accs_auth.Accs_Auth_User;
import jwt.backend.repository.GlobalApprovalLogRepository;
import jwt.backend.repository.RecentVisitorsRepository;
import jwt.backend.repository.settings.approval_flow.ApprovalWorkFlowStatusRepository;
import jwt.backend.service.user_management.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final ApprovalWorkFlowStatusRepository approvalWorkFlowStatusRepository;
    private final AuthService authService;
    private final GlobalApprovalLogRepository globalApprovalLogRepository;
    private final RecentVisitorsRepository recentVisitorsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public static <T> T checkIfValueIsNull(T value) {
        if (value == null || value.toString().isEmpty() || value.toString().equalsIgnoreCase("null")) {
            return null;
        } else {
            return value;
        }
    }

    @Override
    public Long findStatusIdByStatusCode(String code) {
        return approvalWorkFlowStatusRepository.findByCodeAndDeletedAtNull(code).getId();
    }

    @Override
    public ApprovalWorkFlowStatus findDraftStatus() {
        return approvalWorkFlowStatusRepository.findByCodeAndDeletedAtNull(ApprovalStatusEnum.DRAFT.getValue());
    }


    @Override
    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphanumeric(20);
    }

    @Override
    public RecentVisitors recentVisitors(HttpServletRequest request, Accs_Auth_User user, String visitType) {
        // Null checks
        if (request == null || user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("Invalid request or user data.");
        }

        RecentVisitors recentVisitors = new RecentVisitors();

        recentVisitors.setIpAddress(getClientIpAddr(request));

        try {
            recentVisitors.setDevice(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ex) {
            // Log the error and set a default value for the device name or handle it as per your requirement
            log.error("Error getting local host name: " + ex.getMessage());
            recentVisitors.setDevice(CustomMessage.DEFAULT_DEVICE_NAME);
        }

        recentVisitors.setUsername(user.getUsername());
        recentVisitors.setVisitorName(user.getFullName() != null ? user.getFullName() : "Unknown");
        recentVisitors.setVisitorRole(user.getRole().getRole());
        recentVisitors.setVisitType(visitType);
        recentVisitors.setVisitorEmail(user.getEmail());
        recentVisitors.setVisitedAt(getCurrentDateTime());

        try {
            log.info("Inside the recent visitor save method and trying to save the visitor!");
            return recentVisitorsRepository.save(recentVisitors);
        } catch (DataAccessException ex) {
            log.error("Error saving recent visitor data to the database: " + ex.getMessage());
            throw new RuntimeException(CustomMessage.USER_FRIENDLY_ERROR_MESSAGE, ex);
        }
    }


    private String getClientIpAddr(HttpServletRequest request) {
        // Define the list of header names to check for IP address
        List<String> headerNames = Arrays.asList(
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "HTTP_VIA",
                "REMOTE_ADDR"
        );

        // Use stream API to find the first non-null and non-empty IP address
        return headerNames.stream()
                .map(request::getHeader)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty() && !s.equalsIgnoreCase("unknown"))
                .findFirst()
                .orElse(request.getRemoteAddr());
    }

    @Override
    public List<RecentVisitors> findVisitorsList(String search, Integer page, Integer size) {
        return recentVisitorsRepository.recentVisitorsList(search, PageRequest.of(page, size));
    }

    @Override
    public Long visitorsCount() {
        return recentVisitorsRepository.countAllByVisitedAtNotNull();
    }

    @Override
    public Date getCurrentDateTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dhaka"));
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Dhaka"));
        return Date.from(now.atZone(ZoneId.of("Asia/Dhaka")).toInstant());
    }

    @Override
    public <T> long getCountWithPredicate(Predicate predicate, Class<T> entityClass) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> root = countQuery.from(entityClass);
        countQuery.select(cb.count(root)).where(predicate);
        TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public String formatToTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split("\\s");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }

    @Override
    public String formatBoolean(Boolean value) {
        if (value == null) {
            return "No"; // Default to "No" if the value is null
        }
        return value ? "Yes" : "No";
    }

    @Override
    public Double roundToNDecimalPlaces(Double value, int n) {
        if (value == null || n < 0) {
            return null;
        }

        double multiplier = Math.pow(10, n);

        return Math.round(value * multiplier) / multiplier;
    }

    @Override
    public Double ceilNumber(Double value) {
        return value != null ? Math.ceil(value) : null;
    }

    @Override
    public Double transformDoubleNumber(Double value) {
        if (value != null) {
            double numericValue = value;

            double decimal = numericValue % 1;
            double roundedValue = numericValue - decimal + (decimal >= 0.01 && decimal <= 0.5 ? 0.5 : 1);

            if (roundedValue == 1) {
                return null; // Return null if the rounded value is 1
            }

            return roundedValue;
        }

        return null; // Return null if the input value is null
    }

    public static boolean nullCheck(Object input) {
        if (input == null) {
            return false;
        }
        if (input instanceof String strInput) {
            return !strInput.isEmpty() && !strInput.equalsIgnoreCase("null") && !strInput.trim().isEmpty();
        }
        if (input instanceof Long longInput) {
            return longInput != 0;
        }
        return true;
    }
}
