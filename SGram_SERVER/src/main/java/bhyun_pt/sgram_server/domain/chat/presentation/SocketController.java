package bhyun_pt.sgram_server.domain.chat.presentation;

import bhyun_pt.sgram_server.domain.chat.presentation.dto.request.SendChatReqeust;
import bhyun_pt.sgram_server.domain.chat.presentation.service.SendChatService;
import bhyun_pt.sgram_server.global.socket.property.SocketProperty;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SocketController {

    private final SendChatService sendChatService;

    @OnEvent(SocketProperty.CHAT)
    public void sendChat(@RequestBody @Valid SocketIOClient client, SendChatReqeust sendChatReqeust) {
        sendChatService.sendMessage(client, sendChatReqeust);
    }
}
