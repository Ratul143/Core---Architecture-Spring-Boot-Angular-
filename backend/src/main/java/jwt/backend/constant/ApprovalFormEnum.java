package jwt.backend.constant;

import lombok.Getter;

@Getter
public enum ApprovalFormEnum {
    EXAMPLE_FORM("S0001"),
    ;

    public final String form;

    ApprovalFormEnum(String form) {
        this.form = form;
    }
}
