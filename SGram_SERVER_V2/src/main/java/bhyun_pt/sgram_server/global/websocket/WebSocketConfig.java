package bhyun_pt.sgram_server.global.websocket;

import bhyun_pt.sgram_server.domain.chat.service.SendChatService;
import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import bhyun_pt.sgram_server.global.websocket.handler.ChatWebSocketHandler;
import bhyun_pt.sgram_server.global.websocket.interceptor.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final SendChatService sendChatService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new ChatWebSocketHandler(sendChatService,jwtTokenProvider), "ws/chat")
                .addInterceptors(new JwtHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

}
