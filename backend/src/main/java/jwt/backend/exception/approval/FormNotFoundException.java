package jwt.backend.exception.approval;

public class FormNotFoundException extends Exception {
    public FormNotFoundException() {
    }

    public FormNotFoundException(String message) {
        super(message);
    }
}
