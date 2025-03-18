package kit.se.capstone2.auth.domain.repository;

import kit.se.capstone2.auth.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findById(String id);

}
