package jwt.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ZeroValueException extends RuntimeException {
    public ZeroValueException() {
        super();
    }

    public ZeroValueException(String message) {
        super(message);
    }
}
