package bhyun_pt.sgram_server.domain.user.exception;

import bhyun_pt.sgram_server.global.error.CustomException;
import bhyun_pt.sgram_server.global.error.ErrorCode;

public class PasswordMisMatchException extends CustomException {

    public static final CustomException EXCEPTION = new PasswordMisMatchException();

    public PasswordMisMatchException() {
        super(ErrorCode.PASSWORD_MIS_MATCH);
    }
}
