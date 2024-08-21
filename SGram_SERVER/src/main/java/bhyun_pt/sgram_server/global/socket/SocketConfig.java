package bhyun_pt.sgram_server.global.socket;

import bhyun_pt.sgram_server.global.socket.exception.SocketExceptionListener;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SocketConfig extends com.corundumstudio.socketio.SocketConfig {

    @Value("${socket.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);

        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(port);
        configuration.setOrigin("*");
        configuration.setSocketConfig(socketConfig);
        configuration.setExceptionListener(new SocketExceptionListener());
        configuration.setAllowCustomRequests(true);

        return new SocketIOServer(configuration);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }
}
