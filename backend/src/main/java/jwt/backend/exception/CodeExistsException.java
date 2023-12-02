package jwt.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class CodeExistsException extends RuntimeException {
    public CodeExistsException() {
        super();
    }

    public CodeExistsException(String message) {
        super(message);
    }
}
