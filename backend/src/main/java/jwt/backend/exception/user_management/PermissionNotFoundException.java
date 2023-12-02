package jwt.backend.exception.user_management;

public class PermissionNotFoundException extends Exception {
    public PermissionNotFoundException(String message) {
        super(message);
    }
}
