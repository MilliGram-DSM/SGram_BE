package bhyun_pt.sgram_server.domain.chat.service;

import bhyun_pt.sgram_server.domain.chat.domain.entity.ChatEntity;
import bhyun_pt.sgram_server.domain.chat.domain.repository.ChatRepository;
import bhyun_pt.sgram_server.domain.chat.presentation.dto.request.ChatRequest;
import bhyun_pt.sgram_server.domain.chat.presentation.dto.response.ChatResponse;
import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.domain.repositry.UserRepository;
import bhyun_pt.sgram_server.domain.user.exception.UserNotFoundException;
import bhyun_pt.sgram_server.domain.user.facade.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.hibernate.query.sqm.tree.SqmNode.log;


@Service
@RequiredArgsConstructor
public class SendChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;

    @Transactional
    public ChatResponse saveChatMessage(WebSocketSession session, ChatRequest chatRequest) {

        String accountId = (String) session.getAttributes().get("account_id");
        log.info("Jwt 유저 식별 : " + accountId);
        Long userId = userFacade.getUserId(accountId);
        log.info("Jwt 유저 식별 : " + userId);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
        String text = chatRequest.getMessage();

        chatRepository.save(ChatEntity.builder()
                        .accountId(accountId)
                        .message(text)
                        .userEntity(userEntity)
                .build());

        return new ChatResponse(accountId, chatRequest.getMessage());
    }
}
