package bhyun_pt.sgram_server.domain.user.facade;

import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.domain.repositry.UserRepository;
import bhyun_pt.sgram_server.domain.user.exception.UserNotFoundException;
import bhyun_pt.sgram_server.global.socket.util.SocketUtil;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser(SocketIOClient socketIOClient) {
        return getAccount(SocketUtil.getUserId(socketIOClient));
    }

    public UserEntity getAccount(Long id) {
       return userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public UserEntity getAccount(String accountId) {
        return userRepository.findByAccountId(accountId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }
}
