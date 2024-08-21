package bhyun_pt.sgram_server.domain.user.presentation.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpRequest {

    @Size(min = 3, max = 20)
    @NotBlank
    private String accountId;


    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "올바른 전화번호 형식을 입력해 주세요.")
    private String phone;
}
