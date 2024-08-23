package bhyun_pt.sgram_server.global.security.exception;

import bhyun_pt.sgram_server.global.error.CustomException;
import bhyun_pt.sgram_server.global.error.ErrorCode;

public class InValidTokenException extends CustomException {

    public static final CustomException Exception = new InValidTokenException();

    public InValidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
