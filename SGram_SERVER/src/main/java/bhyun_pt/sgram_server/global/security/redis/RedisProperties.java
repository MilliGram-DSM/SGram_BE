package bhyun_pt.sgram_server.global.security.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

    private final String host;

    private final int port;

}
