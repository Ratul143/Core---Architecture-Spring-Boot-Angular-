package jwt.backend.exception.user_management;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ComponentsExistsException extends RuntimeException {
    public ComponentsExistsException() {
        super();
    }

    public ComponentsExistsException(String message) {
        super(message);
    }
}
