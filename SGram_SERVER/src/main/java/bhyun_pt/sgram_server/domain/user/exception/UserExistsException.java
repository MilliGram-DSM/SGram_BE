package bhyun_pt.sgram_server.domain.user.exception;

import bhyun_pt.sgram_server.global.error.CustomException;
import bhyun_pt.sgram_server.global.error.ErrorCode;

public class UserExistsException extends CustomException {

    public static final CustomException EXCEPTION = new UserExistsException();

    public UserExistsException() {
        super(ErrorCode.ALREADY_ACCOUNDID_EXISTS);
    }
}
