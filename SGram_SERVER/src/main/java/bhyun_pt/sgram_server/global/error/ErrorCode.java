package bhyun_pt.sgram_server.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {



    PASSWORD_MIS_MATCH(401, "Password Mis Match"),

    EXPIRED_TOKEN(401, "Expired Token"),

    INVALID_TOKEN(401, "Invalid Token"),

    USER_NOT_FOUND(404, "User Not Found"),

    ALREADY_ACCOUNDID_EXISTS(409, "This account already exists"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;
}
