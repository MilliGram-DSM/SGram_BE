package bhyun_pt.sgram_server.domain.chat.domain.entity;

import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @Column(nullable = false, length = 100)
    private String message;

    private String accountId;

    @Builder
    public ChatEntity(String message, String accountId, UserEntity userEntity) {
        this.accountId = accountId;
        this.message = message;
        this.userEntity = userEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userEntity;

}
