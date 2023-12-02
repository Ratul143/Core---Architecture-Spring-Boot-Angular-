package jwt.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CommonException extends RuntimeException {
    public CommonException() {
        super();
    }

    public CommonException(String message) {
        super(message);
    }
}
