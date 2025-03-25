package kit.se.capstone2.common.init;

import jakarta.annotation.PostConstruct;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.domain.repository.AccountRepository;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer {
	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.init();
	}

	@Component
	@RequiredArgsConstructor
	@Transactional
	@Log4j2
	public static class InitService {

		private final PasswordEncoder passwordEncoder;
		private final ClientUserRepository clientUserRepository;

		public void init() {
			Account adminAccount = Account.builder()
					.username("admin")
					.password(passwordEncoder.encode("***REMOVED***7540!"))
					.role(Role.ROLE_ADMIN)
					.approvalStatus(ApprovalStatus.APPROVED).build();

			ClientUser clientUser = ClientUser.builder()
					.name("관리자")
					.nickname("관리자")
					.birthDate(LocalDate.of(2000,02,21))
					.phoneNumber("010-4262-5325")
					.build();

			clientUser.addAccount(adminAccount);
			clientUserRepository.save(clientUser);
		}
	}
}
