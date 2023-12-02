package jwt.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NullValueException extends RuntimeException {
    public NullValueException() {
        super();
    }

    public NullValueException(String message) {
        super(message);
    }
}
