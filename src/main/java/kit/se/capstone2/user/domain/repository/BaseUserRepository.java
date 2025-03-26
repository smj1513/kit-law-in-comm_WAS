package kit.se.capstone2.user.domain.repository;

import kit.se.capstone2.user.domain.model.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
}
