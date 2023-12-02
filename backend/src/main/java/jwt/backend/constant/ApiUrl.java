package jwt.backend.constant;

public abstract class ApiUrl {

    public static final String SERVER_API = "*";

    public static final String BASE_API = "/api/v1";

    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    public static final String CREATE_OR_UPDATE = "/create-or-update";
    public static final String DELETE = "/delete";
    public static final String LIST = "/list";
    public static final String FIND_ALL = "/find-all";
    public static final String FIND_ALL_BY_KEYWORD = "/find-all-by-keyword";
    public static final String ITEMS = "/items";
    public static final String EMPLOYEES = "/employees";
    public static final String EMPLOYEE_TYPE = "/employee-type";
    public static final String AUTH_USERS = "/auth-users";
    public static final String FIND_ALL_SIDENAV_ELEMENTS = "/find-all-sidenav-elements";
    public static final String FIND_ALL_BY_ROLE_ID = "/find-all-by-role-id";
    public static final String FIND_ALL_BY_USER_AND_ROLE_ID = "/find-all-by-user-and-role-id";
    public static final String REVERT_TO_DEFAULT_ROLE = "/revert-to-default-role";
    public static final String EXPORT_DATA = "/export-data";
    public static final String GENERATE_REPORT = "/generate-report";
    public static final String GENERATE_PDF_REPORT = "/generate-pdf-report";
    public static final String GENERATE_XLSX_REPORT = "/generate-xlsx-report";

//    User-Management
    public static final String USER = "/user";
    public static final String LOGIN = "/login";
    public static final String SIGN_IN = "/sign-in";
    public static final String ADD = "/add";
    public static final String FIND_USER = "/find/{username}";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String UPDATE_USER_INFO = "/update-user-info";
    public static final String DELETE_BY_ID= "/delete/{id}";
    public static final String DELETE_USER = "/delete/{username}";
    public static final String RESET_USER_PASSWORD = "/reset-user-password";
    public static final String CHANGE_USER_PASSWORD = "/change-user-password";
    public static final String UPDATE_PROFILE_IMAGE = "/update-profile-image";
    public static final String GET_PROFILE_IMAGE = "/image/{username}/{fileName}";
    public static final String GET_TEMP_PROFILE_IMAGE = "/image/profile/{username}";
    public static final String COUNT = "/count";
    public static final String CHANGE_PASSWORD = "/change-password";
    public static final String VISITOR = "/visitor";
    public static final String RECENT_VISITOR = "/visit";
    public static final String HR_EMPLOYEES = "/hr-employees";

//    Module-Management
    public static final String COMPONENT = "/component";
    public static final String MODULE = "/module";
    public static final String SUB_MODULE = "/sub-module";
    public static final String ROLE_BASED_PERMISSION = "/role-based-permission";
    public static final String USER_BASED_PERMISSION = "/user-based-permission";
    public static final String ROLE = "/role";
    public static final String FIND_ALL_BY_MODULE_ID = "/find-all-by-module-id";
    public static final String FIND_ALL_BY_SUB_MODULE_ID = "/find-all-by-sub-module-id";
    public static final String FIND_BY_PATH = "/find-by-path";
    public static final String FIND_ALL_LIKE_UNIQUE_KEY = "/find-all-like-unique-key";
    public static final String FIND_ALL_COMPONENT_BY_SUB_MODULE_UNIQUE_KEY_AND_KEYWORD = "/find-all-component-by-sub-module-unique-key-keyword";
    public static final String FIND_ALL_BY_UNIQUE_KEY_AND_KEYWORD = "/find-all-by-unique-key-keyword";
    public static final String FIND_ALL_ORPHAN_COMPONENTS_BY_MODULE_UNIQUE_KEY = "/find-all-orphan-components-by-module-unique-key";
    public static final String FIND_ROLE_BY_ID = "/find-role-by-id";
    public static final String FIND_SUB_MODULE_BY_UNIQUE_KEY = "/find-sub-module-by-unique-key";
    public static final String FIND_ALL_SUB_MODULES_BY_MODULE_ID = "/find-all-sub-modules-by-module-id";
    public static final String FIND_MODULE_BY_UNIQUE_KEY = "/find-module-by-unique-key";
    public static final String FIND_MODULE_COMPONENTS = "/find-module-components";


    // File-Uploading
    public static final String FILE = "/file";
    public static final String GET_USER_IMAGE = "/process-file/user/{fileName}";
    public static final String TEMP_PROFILE_IMAGE = "/process-file/temp-file/{fileName}";

    // Approval-Flow
    public static final String APPROVAL_WORK_FLOW = "/approval-work-flow";
    public static final String APPROVAL_WORK_FLOW_PERMISSION = "/approval-work-flow-permission";
    public static final String APPROVAL_WORK_FLOW_FOR = "/approval-work-flow-for";
    public static final String APPROVAL_WORK_FLOW_STATUS = "/approval-work-flow-status";
    public static final String APPROVAL_WORK_FLOW_STEPS = "/approval-work-flow-steps";
    public static final String USER_FORM_APPROVAL_FLOW = "/user-form-approval-flow";
    public static final String LOGS = "/logs";
    public static final String LOG_LIST = "/log-list";
    public static final String GET_NEXT_STEP = "/get-next-step";
    public static final String GET_PREVIOUS_STEP = "/get-previous-step";
    public static final String GET_BY_FORM = "/get-by-form";
    public static final String FIND_BY_FORM_CODE = "/find-by-form-code";
    public static final String FIND_BY_FORM_UNIQUE_ID = "/find-by-form-unique-id";


}
