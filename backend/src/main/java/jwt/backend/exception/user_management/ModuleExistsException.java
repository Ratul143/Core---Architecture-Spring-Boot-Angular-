package jwt.backend.exception.user_management;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ModuleExistsException extends RuntimeException {
    public ModuleExistsException() {
        super();
    }

    public ModuleExistsException(String message) {
        super(message);
    }
}
