package bhyun_pt.sgram_server.global.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {

    private String secretKey;
    private Long accessExp;
    private Long refreshExp;
    private String header;
    private String prefix;

}
