package bhyun_pt.sgram_server.global.socket.connect;

import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.facade.UserFacade;
import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import bhyun_pt.sgram_server.global.socket.property.ClientProperty;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@RequiredArgsConstructor
@Component
public class SocketConnectListener {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserFacade userFacade;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = jwtTokenProvider.resolveToken(client);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        log.info(token);
        String accountId = authentication.getName();
        UserEntity userEntity = userFacade.getAccount(accountId);
        client.set(ClientProperty.USER_KEY, userEntity.getUserId());
    }

}
