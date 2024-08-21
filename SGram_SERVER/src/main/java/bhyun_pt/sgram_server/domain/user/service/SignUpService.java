package bhyun_pt.sgram_server.domain.user.service;

import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import bhyun_pt.sgram_server.domain.user.domain.repositry.UserRepository;
import bhyun_pt.sgram_server.domain.user.exception.UserExistsException;
import bhyun_pt.sgram_server.domain.user.presentation.dto.request.SignUpRequest;
import bhyun_pt.sgram_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void execute(SignUpRequest signUpRequest) {
        if(userRepository.findByAccountId(signUpRequest.getAccountId()).isPresent()) {
            throw UserExistsException.EXCEPTION;
        }

        userRepository.save(UserEntity.builder()
                .accountId(signUpRequest.getAccountId())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .phone(signUpRequest.getPhone())
                .build());
    }
}
