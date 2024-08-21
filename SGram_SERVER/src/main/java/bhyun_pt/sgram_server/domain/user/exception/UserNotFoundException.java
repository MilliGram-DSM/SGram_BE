package bhyun_pt.sgram_server.domain.user.exception;

import bhyun_pt.sgram_server.global.error.CustomException;
import bhyun_pt.sgram_server.global.error.ErrorCode;

public class UserNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new UserNotFoundException();

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
