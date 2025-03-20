package kit.se.capstone2.user.domain.repository;

import kit.se.capstone2.user.domain.model.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientUserRepository extends JpaRepository<ClientUser, Long> {
}
