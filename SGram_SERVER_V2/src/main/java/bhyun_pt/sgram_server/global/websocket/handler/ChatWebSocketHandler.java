package bhyun_pt.sgram_server.global.websocket.handler;

import bhyun_pt.sgram_server.domain.chat.domain.entity.ChatEntity;
import bhyun_pt.sgram_server.domain.chat.presentation.dto.request.ChatRequest;
import bhyun_pt.sgram_server.domain.chat.presentation.dto.response.ChatResponse;
import bhyun_pt.sgram_server.domain.chat.service.SendChatService;
import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.util.HashSet;
import java.util.Set;

import static org.hibernate.query.sqm.tree.SqmNode.log;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private JwtTokenProvider jwtTokenProvider;
    private  SendChatService sendChatService;
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private ObjectMapper mapper = new ObjectMapper();


    public ChatWebSocketHandler(SendChatService sendChatService, JwtTokenProvider jwtTokenProvider) {
        this.sendChatService = sendChatService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    String accountId;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = (String) session.getAttributes().get("token");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim(); // "Bearer " 제거 및 공백 제거
        }

        if(jwtTokenProvider.validateToken(token)) {
            accountId = jwtTokenProvider.getAccountIdFromJWT(token);
            Long userId = jwtTokenProvider.getUserIdFromJWT(accountId);

            log.info(accountId);
            log.info(userId);

            session.getAttributes().put("user_id", userId);
            session.getAttributes().put("account_id", accountId);
        }

        else {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("찾을수 없는 Jwt 토큰"));
        }

        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ChatRequest chatRequest = mapper.readValue(message.getPayload(), ChatRequest.class);

        ChatResponse chatResponse = sendChatService.saveChatMessage(session,chatRequest);

        for(WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsString(chatResponse)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}
