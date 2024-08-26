package bhyun_pt.sgram_server.domain.chat.service;

import bhyun_pt.sgram_server.domain.chat.domain.entity.ChatEntity;
import bhyun_pt.sgram_server.domain.chat.domain.repository.ChatRepository;
import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.domain.repositry.UserRepository;
import bhyun_pt.sgram_server.domain.user.exception.UserNotFoundException;
import bhyun_pt.sgram_server.domain.user.facade.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


@Service
@RequiredArgsConstructor
public class SendChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public ChatEntity saveChatMessage(WebSocketSession session,TextMessage textMessage) {

        String accountId = (String) session.getAttributes().get("account_id");
        Long userId = userFacade.getUserId(accountId);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
        String text = textMessage.getPayload();

        return chatRepository.save(ChatEntity.builder()
                        .accountId(accountId)
                        .message(text)
                        .userEntity(userEntity)
                .build());
    }
}
