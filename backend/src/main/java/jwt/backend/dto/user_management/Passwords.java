package jwt.backend.dto.user_management;

import lombok.*;

@Getter
public class Passwords {
    private final String hashedPassword;
    private final String plainPassword;

    public Passwords(String hashedPassword, String plainPassword) {
        this.hashedPassword = hashedPassword;
        this.plainPassword = plainPassword;
    }

}

