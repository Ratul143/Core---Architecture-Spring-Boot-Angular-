package jwt.backend.exception.user_management;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
