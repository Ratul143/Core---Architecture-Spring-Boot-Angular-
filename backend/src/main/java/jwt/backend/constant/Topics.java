package jwt.backend.constant;

import lombok.Getter;

@Getter
public enum Topics {

    USER("User", 100),
    MODULE_MANAGEMENT("Module_Management", 101),
    SETTINGS("Settings",  102);

    private final String name;

    private final int code;

    private Topics(String name, int code) {
        this.name = name;
        this.code = code;
    }

}
