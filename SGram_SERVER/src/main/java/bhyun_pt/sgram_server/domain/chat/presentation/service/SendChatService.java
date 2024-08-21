package bhyun_pt.sgram_server.domain.chat.presentation.service;

import bhyun_pt.sgram_server.domain.chat.domain.entity.ChatEntity;
import bhyun_pt.sgram_server.domain.chat.domain.repository.ChatRepository;
import bhyun_pt.sgram_server.domain.chat.presentation.dto.request.SendChatReqeust;
import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.domain.repositry.UserRepository;

import bhyun_pt.sgram_server.global.socket.property.ClientProperty;
import bhyun_pt.sgram_server.global.socket.property.SocketProperty;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SendChatService {

    private final UserRepository userRepository;
    private final SocketIOServer socketIOServer;
    private final ChatRepository chatRepository;

    @Transactional
    public void sendMessage(SocketIOClient client, SendChatReqeust sendChatReqeust) {
        Optional<UserEntity> userEntity = userRepository.findByAccountId(client.get(ClientProperty.USER_KEY));

        ChatEntity chatEntity = chatRepository.save(
                ChatEntity.builder()
                        .accountId(String.valueOf(userEntity))
                        .content(sendChatReqeust.getMessage())
                        .build());

        socketIOServer.getAllClients().forEach(c -> {
            c.sendEvent(SocketProperty.CHAT, chatEntity.getContent());
        });
    }

}
