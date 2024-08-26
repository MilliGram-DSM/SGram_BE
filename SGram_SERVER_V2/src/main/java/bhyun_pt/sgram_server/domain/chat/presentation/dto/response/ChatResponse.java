package bhyun_pt.sgram_server.domain.chat.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatResponse {

    private String accountId;

    private String message;

}
