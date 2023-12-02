package jwt.backend.exception.approval;

public class StatusNotFoundException extends Exception {
    public StatusNotFoundException() {
    }

    public StatusNotFoundException(String message) {
        super(message);
    }
}
