package kit.se.capstone2.auth.domain.repository;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query(value = "select account from Account account where account.username = :username and account.approvalStatus = 'APPROVED'")
	Account findByUsername(String username);


	@Query(value = "select ac from Account ac where ac.approvalStatus = :approvalStatus")
	List<Account> findByApprovalStatus(ApprovalStatus approvalStatus);
}
