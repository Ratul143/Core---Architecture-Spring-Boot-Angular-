package jwt.backend.exception;

/**
 * @author Jaber
 * @Date 8/31/2022
 * @Time 8:55 PM
 * @Project backend
 */
public class DuplicateFoundException extends Exception {
    public DuplicateFoundException(String message) {
        super(message);
    }
}
