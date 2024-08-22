package bhyun_pt.sgram_server.global.security.jwt;

import bhyun_pt.sgram_server.domain.refreshtoken.RefreshToken;
import bhyun_pt.sgram_server.domain.refreshtoken.RefreshTokenRepository;
import bhyun_pt.sgram_server.global.security.exception.ExpiredTokenException;
import bhyun_pt.sgram_server.global.security.auth.AuthDetailsService;
import com.corundumstudio.socketio.SocketIOClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;


@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final JwtProperty jwtProperty;
    private final AuthDetailsService authDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateToken(String accountId) {

        Date now = new Date();

        return Jwts.builder()
                .setSubject(accountId)
                .claim("type","access")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperty.getAccessExp() * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
                .compact();
    }

    public String generateRefreshToken(String accountId) {

        Date now = new Date();

        String refreshToken = Jwts.builder()
                .claim("type","refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtProperty.getRefreshExp() * 10000))
                .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
                .compact();

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .accountId(accountId)
                        .token(refreshToken)
                        .ttl(jwtProperty.getRefreshExp())
                        .build());

        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        UserDetails userDetails = authDetailsService.loadUserByUsername(claims.getSubject());
        log.info(claims.toString());
        log.info(userDetails.toString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(jwtProperty.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw ExpiredTokenException.EXCEPTION;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperty.getHeader());

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperty.getPrefix())
        && bearerToken.length() > jwtProperty.getPrefix().length()+1) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String resolveToken(SocketIOClient socketIOClient) {

        String bearerToken = socketIOClient.getHandshakeData().getHttpHeaders().get(jwtProperty.getHeader());

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProperty.getPrefix())
                && bearerToken.length() > jwtProperty.getPrefix().length() + 1) {
            return bearerToken.substring(jwtProperty.getPrefix().length()+1);
        }
        return null;
    }
}
