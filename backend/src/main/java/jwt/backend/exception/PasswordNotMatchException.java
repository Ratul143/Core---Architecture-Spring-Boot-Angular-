package jwt.backend.exception;

public class PasswordNotMatchException extends Exception {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
