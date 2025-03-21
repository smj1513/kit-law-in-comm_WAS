package kit.se.capstone2.user.application;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAppService {
	private final ClientUserRepository clientUserRepository;
	private final LawyerRepository lawyerRepository;

	public UserResponse.General joinGeneralUser(UserRequest.JoinGeneralUser request) {
		Account account = Account.builder()
				.username(request.getUsername())
				.password(request.getPassword())
				.role(Role.ROLE_USER)
				.approvalStatus(ApprovalStatus.APPROVED)
				.build();
		ClientUser user = ClientUser.builder()
				.name(request.getName())
				.nickname(request.getNickname())
				.birthDate(request.getBirthDate())
				.build();
		user.addAccount(account);
		ClientUser user1 = clientUserRepository.save(user);
		Account account1 = user1.getAccount();
		return UserResponse.General.builder().username(account1.getUsername()).role(account1.getRole()).approvalStatus(account1.getApprovalStatus()).build();
	}

	//TODO : 증빙서류 업로드 기능 추가
	public UserResponse.LawyerRes joinLawyer(UserRequest.JoinLawyer request) {
		Account account = Account.builder()
				.username(request.getUsername())
				.password(request.getPassword())
				.role(Role.ROLE_LAWYER)
				.approvalStatus(ApprovalStatus.WAITING)
				.build();
		return null;
	}
}
