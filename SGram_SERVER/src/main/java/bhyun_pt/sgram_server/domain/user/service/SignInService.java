package bhyun_pt.sgram_server.domain.user.service;

import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.domain.repositry.UserRepository;
import bhyun_pt.sgram_server.domain.user.exception.PasswordMisMatchException;
import bhyun_pt.sgram_server.domain.user.exception.UserNotFoundException;
import bhyun_pt.sgram_server.domain.user.presentation.dto.request.SignInRequest;
import bhyun_pt.sgram_server.domain.user.presentation.dto.response.TokenResponse;
import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse execute(SignInRequest signInRequest) {
        UserEntity userEntity = userRepository.findByAccountId(signInRequest.getAccountId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if(!passwordEncoder.matches(signInRequest.getPassword(), userEntity.getPassword())) {
            throw PasswordMisMatchException.EXCEPTION;
        }

        String accessToken = jwtTokenProvider.generateToken(signInRequest.getAccountId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(signInRequest.getAccountId());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
