package kit.se.capstone2.auth.domain.repository;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query(value = "select account from Account account where account.username = :username and account.approvalStatus = 'APPROVED'")
	Account findByUsername(String username);
}
