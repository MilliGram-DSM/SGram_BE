package bhyun_pt.sgram_server.global.socket.connect;

import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import bhyun_pt.sgram_server.global.socket.property.ClientProperty;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SocketJwtController {

    public static final ConcurrentMap<String, SocketIOClient> socketIOClientMap
            = new ConcurrentHashMap<>();
    private final JwtTokenProvider jwtTokenProvider;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getHttpHeaders().get("Authorization");
        log.info(token);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        socketIOClientMap.put(authentication.getName(), client);
        client.set(ClientProperty.USER_KEY,authentication.getName());
    }



    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        socketIOClientMap.remove(client.get(ClientProperty.USER_KEY).toString());
    }
}
