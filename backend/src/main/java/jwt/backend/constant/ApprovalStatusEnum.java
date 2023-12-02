package jwt.backend.constant;

import lombok.Getter;

@Getter
public enum ApprovalStatusEnum {
    DEFAULT("DEFAULT"), // default is set for DRAFT AND SEND_FOR_AUTHORIZATION
    LOG("LOG"),
    ALL("ALL"),
    AFTERWARDS("AFTERWARDS"),
    DRAFT("0000"),
    SEND_FOR_AUTHORIZATION("0001"),
    AUTHORIZE("0002"),
    DELIVERY("0003"),
    APPROVE("0004"),
    SEND_TO_OUTLET("0005"),
    FINALIZE("0006");

    public final String status;

    ApprovalStatusEnum(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
