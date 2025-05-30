package kit.se.capstone2.user.domain.repository;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LawyerRepository extends JpaRepository<Lawyer, Long> {

	@Query("select l from Lawyer l where l.account.approvalStatus = :approvalStatus")
	Page<Lawyer> findAllByApprovalStatus(ApprovalStatus approvalStatus, Pageable pageRequest);

	Lawyer findByAccount(Account account);
}
