package bhyun_pt.sgram_server.domain.user.domain;

import bhyun_pt.sgram_server.domain.chat.domain.entity.ChatEntity;
import bhyun_pt.sgram_server.domain.user.domain.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, length = 20)
    private String accountId;

    @Column(length = 255)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "chatId", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ChatEntity> chats = new ArrayList<>();

    @Builder
    public UserEntity(String accountId, String password, String phone) {
        this.accountId = accountId;
        this.password = password;
        this.phone = phone;
    }
}
