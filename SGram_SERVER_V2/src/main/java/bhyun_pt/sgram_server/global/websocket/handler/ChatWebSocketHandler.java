package bhyun_pt.sgram_server.global.websocket.handler;

import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

import static org.hibernate.query.sqm.tree.SqmNode.log;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public ChatWebSocketHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = (String) session.getAttributes().get("token");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim(); // "Bearer " 제거 및 공백 제거
        }

        if(jwtTokenProvider.validateToken(token)) {
            String accountId = jwtTokenProvider.getAccountIdFromJWT(token);
            Long userId = jwtTokenProvider.getUserIdFromJWT(accountId);

            log.info(accountId);
            log.info(userId);

            session.getAttributes().put("user_id", userId);
            session.getAttributes().put("account_id", accountId);
        }

        else {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("찾을수 없는 Jwt 토큰"));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long userId = (Long) session.getAttributes().get("user_id");
        String accountId = (String) session.getAttributes().get("account_id");
    }


}
