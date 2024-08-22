package bhyun_pt.sgram_server.global.socket;

import bhyun_pt.sgram_server.global.socket.exception.SocketExceptionListener;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class SocketConfig extends com.corundumstudio.socketio.SocketConfig {

    @Value("${socket.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {

        SocketConfig socketConfig = new SocketConfig();
        SocketExceptionListener socketExceptionListener = new SocketExceptionListener();
        socketConfig.setReuseAddress(true); // 포트 주소 재사용 ture

        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(port); // 포트 설정
        configuration.setOrigin("*"); // 출처 설정
        configuration.setAllowHeaders("*"); // 헤더 허용 설정
        configuration.setSocketConfig(socketConfig); // 소켓 컨피그 설정
        configuration.setExceptionListener(socketExceptionListener); // 예외 처리 리스너 설정
        configuration.setAllowCustomRequests(true); // CORS 허용

        return new SocketIOServer(configuration); // 컨피규레이션 반환
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }
}
