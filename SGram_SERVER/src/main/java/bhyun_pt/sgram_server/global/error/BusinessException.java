package bhyun_pt.sgram_server.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
}
