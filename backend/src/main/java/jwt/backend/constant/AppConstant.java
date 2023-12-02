package jwt.backend.constant;

import static org.springframework.http.MediaType.*;

/**
 * @author Jaber
 * @Date 8/24/2022
 * @Project backend
 */
public class AppConstant {
    public static final String APPLICATION_URL = "http://0.0.0.0/backend_services/";

    //<<=================================================================== Folder Path =================================================>>

    private static final String BASE_FOLDER = "E://backend_services/";
    public static final String  REPORT_FOLDER = "";
    public static final String REPORT_DIRECTORY = BASE_FOLDER + "reports/";
    public static final String USER_FOLDER = BASE_FOLDER + "user/";

    //<<=================================================================== Image Path =================================================>>
    public static final String GLOBAL_IMAGE_PATH = "/file/process-file/";

    //<<===================================================================Default Image Path =================================================>>
    public static final String DEFAULT_IMAGE_PATH = "/file/process-file/temp-file/";

    public static String getBaseFolder() {
        return BASE_FOLDER;
    }

    public static final String JPG_EXTENSION = "jpg";
    public static final String DOT = ".";
    public static final String FORWARD_SLASH = "/";
    public static final String TEMP_PROFILE_IMAGE_BASE_URL = "https://robohash.org/";
    /* ===============================================================Security Constant=========================================================== */
    public static final long EXPIRATION_TIME = 432_000_000; //5 DAYS EXPRESSED IN MILLISECOND;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";

    public static String getUserDirectory() {
        return getBaseFolder() + "user/";
    }

    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token can't be verified!";
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have enough permission!";
    public static final String OPTION_HTTP_METHOD = "Options";

    public static final String[] PUBLIC_URLS = {
            "/api/v1/user/**",
            "/api/v1/component/**",
            "/api/v1/component-permission/**",
            "/api/v1/module/**",
            "/api/v1/module-for-role/**",
            "/api/v1/sub-module/**",
            "/api/v1/role/**",
            "/api/v1/visitor/**",
            "/api/v1/buyer-form/**",
            "/api/v1/approval-work-flow-permission/**",
            "/api/v1/approval-work-flow/**",
            "/api/v1/approval-work-flow-for/**",
            "/api/v1/approval-work-flow-status/**",
            "/api/v1/approval-work-flow-steps/**",
            "/api/v1/user-form-approval-flow/**",
            "/api/v1/user/image/**",
            "/api/v1/file/**"
    };
    /* ===============================================================User Implementation Constant=========================================================== */
    public static final String USERNAME_ALREADY_EXISTS = "Username already exists";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String NO_USER_FOUND_BY_USERNAME = "No user found by username";
    public static final String FOUND_USER_BY_USERNAME = "Returning found user by username";
    public static final String EMAIL_SENT = "An email with new password with sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully!";
    public static final String NO_USER_FOUND_BY_EMAIL = "No user found for email: ";
    /* ===============================================================Email Constant=========================================================== */
//    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";
//    public static final String USERNAME = "Majedabdullah635@gmail.com";
//    public static final String PASSWORD = "eezpphlcbiobovup";
//    public static final String FROM_EMAIL = "Majedabdullah635@gmail.com";
//    public static final String CC_EMAIL = "Helloworld1663@gmail.com";
//    public static final String EMAIL_SUBJECT = "User management system";
//    public static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";
//    public static final String SMTP_HOST = "mail.smtp.host";
//    public static final String SMTP_AUTH = "mail.smtp.auth";
//    public static final String SMTP_PORT = "mail.smtp.port";
//    public static final int DEFAULT_PORT = 465;
//    public static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
//    public static final String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";


//    Outlook Configuration

    public static final String TRANSFER_PROTOCOL = "smtp";
    public static final String USERNAME = "partner.dal@dekkolegacy.com";
    public static final String PASSWORD = "P@rt7978neR";
    public static final String FROM_EMAIL = "partner.dal@dekkolegacy.com";
    public static final String CC_EMAIL = "";
    public static final String EMAIL_SUBJECT = "User Credentials";
    public static final String SMTP_SERVER = "smtp.office365.com";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String SMTP_PORT = "mail.smtp.port";
    public static final int DEFAULT_PORT = 587;
    public static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    public static final String INITIAL_ENTRY = "INITIAL_ENTRY";
    public static final String BALANCE_UPDATE = "BALANCE_UPDATE";
    public static final String PLUS = "PLUS(+)";
    public static final String MINUS = "MINUS(-)";
    public static final int DECIMAL_PLACES = 3;
    public static String[] ALLOWED_IMAGE_TYPE = {IMAGE_JPEG_VALUE, IMAGE_GIF_VALUE, IMAGE_PNG_VALUE, "image/jpg"};
    public static String[] ALLOWED_DOCUMENT_TYPE = {APPLICATION_PDF_VALUE, APPLICATION_XML_VALUE, "application/xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/word", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "text/plain"};
}


