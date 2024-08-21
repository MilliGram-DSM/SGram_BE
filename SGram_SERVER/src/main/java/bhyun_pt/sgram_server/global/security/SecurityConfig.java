package bhyun_pt.sgram_server.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 이 메소드는 'HttpSecurity' 객체를 사용하여 보안 설정을 구성하고,
        // 'SecurityFilterChain'을 빈으로 등록한다.

        //csrf 보호를 비활성화 한다. 주로 REST API와 같은 stateless 애플리케이션에서 사용된다.
        http
                .csrf(AbstractHttpConfigurer::disable);

        //기본 폼 로그인 방식을 비활성화 한다.
        http
                .formLogin(AbstractHttpConfigurer::disable);
        // UsernameAuthenticationFilter 종료

        //요청에 대한 관한을 설정한다.
        http
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/","/users/join","/users/login").permitAll());
                        // 해당 경로에 대한 모든 접근을 허용한다.


        //세션 설정
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // 세션 : STATELESS, 비활성화 시킴


        return http.build();
        // 필터체인에 결과를 반환
    }
}
