package bhyun_pt.sgram_server.domain.user.domain.repositry;

import bhyun_pt.sgram_server.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByAccountId(String userId);
}
