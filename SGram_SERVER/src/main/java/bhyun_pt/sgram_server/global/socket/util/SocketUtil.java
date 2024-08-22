package bhyun_pt.sgram_server.global.socket.util;

import bhyun_pt.sgram_server.domain.user.exception.UserNotFoundException;
import bhyun_pt.sgram_server.global.socket.property.ClientProperty;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocketUtil {

    public static Long getUserId(SocketIOClient socketIOClient) {

        if(!socketIOClient.has(ClientProperty.USER_KEY)) { // socketIOClient 가 user_key 를 가지고 있는지 식별
            throw UserNotFoundException.EXCEPTION; // 없다면 예외 처리
        }

        return socketIOClient.get(ClientProperty.USER_KEY); // userkey 얻기
    }
}
