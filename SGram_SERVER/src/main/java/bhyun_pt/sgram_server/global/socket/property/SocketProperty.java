package bhyun_pt.sgram_server.global.socket.property;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocketProperty {

    //request
    public static final String CHAT = "chat"; // chat 이벤트 설정

    //response
    public static final String ERROR = "error"; // error 이벤트 설정

}
