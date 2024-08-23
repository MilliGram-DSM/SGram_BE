package bhyun_pt.sgram_server.global.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorReponse {

    private final int status;
    private final String message;
}
