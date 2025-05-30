package kit.se.capstone2.auth.domain.repository;

import jakarta.validation.constraints.Pattern;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query(value = "select account from Account account where account.username = :username and account.approvalStatus = 'APPROVED'")
	Account findByUsername(String username);

	@Query(value = "select ac from Account ac where ac.username = :username and ac.approvalStatus = :approvalStatus")
	Account findByUsernameAndApprovalStatus(String username, ApprovalStatus approvalStatus);

	@Query(value = "select ac from Account ac where ac.approvalStatus = :approvalStatus")
	List<Account> findByApprovalStatus(ApprovalStatus approvalStatus);

	boolean existsByUsername(@Pattern(regexp = "^[a-zA-Z0-9]{2,20}", message = "아이디는 2~20자의 영문 대소문자와 숫자로 이루어져야 합니다.") String username);
}
