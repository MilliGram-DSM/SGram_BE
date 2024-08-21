package bhyun_pt.sgram_server.domain.user.presentation;

import bhyun_pt.sgram_server.domain.user.presentation.dto.request.SignInRequest;
import bhyun_pt.sgram_server.domain.user.presentation.dto.request.SignUpRequest;
import bhyun_pt.sgram_server.domain.user.presentation.dto.response.TokenResponse;
import bhyun_pt.sgram_server.domain.user.service.SignInService;
import bhyun_pt.sgram_server.domain.user.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final SignUpService signUpService;
    private final SignInService signInService;


    @PostMapping("/join")
    public void join(@RequestBody @Valid SignUpRequest signUpRequest) {
        signUpService.execute(signUpRequest);
    }

    @PostMapping("/login")
    public TokenResponse signUp(@RequestBody @Valid SignInRequest signInRequest) {
        return signInService.execute(signInRequest);
    }
}
