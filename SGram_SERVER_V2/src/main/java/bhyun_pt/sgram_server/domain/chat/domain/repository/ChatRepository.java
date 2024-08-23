package bhyun_pt.sgram_server.domain.chat.domain.repository;


import bhyun_pt.sgram_server.domain.chat.domain.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
}
