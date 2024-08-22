package bhyun_pt.sgram_server.global.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SocketRunner implements CommandLineRunner { // CommandLineRunner : 스프링 시작후 바로 실행할 코드를 정의

    private final SocketIOServer socketIOServer;

    @Override
    public void run(String... args) {
        socketIOServer.start(); // 소켓 서버 시작
    }
}
