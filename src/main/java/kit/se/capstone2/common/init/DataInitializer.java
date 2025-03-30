package kit.se.capstone2.common.init;

import jakarta.annotation.PostConstruct;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.model.lawyer.*;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {
	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.init();
		initService.initAIAssistant();
	}

	@Component
	@RequiredArgsConstructor
	@Transactional
	@Log4j2
	public static class InitService {

		private final PasswordEncoder passwordEncoder;
		private final ClientUserRepository clientUserRepository;
		private final LawyerRepository lawyerRepository;

		public void init() {
			Account adminAccount = Account.builder()
					.username("admin")
					.password(passwordEncoder.encode("***REMOVED***7540!"))
					.role(Role.ROLE_ADMIN)
					.approvalStatus(ApprovalStatus.APPROVED).build();

			ClientUser clientUser = ClientUser.builder()
					.name("관리자")
					.nickname("관리자")
					.birthDate(LocalDate.of(2000, 02, 21))
					.phoneNumber("010-4262-5325")
					.build();

			clientUser.addAccount(adminAccount);
			clientUserRepository.save(clientUser);
		}

		public void initAIAssistant() {
			Account aiAccount = Account.builder()
					.username("minj21")
					.password(passwordEncoder.encode("mjmj0221!"))
					.role(Role.ROLE_LAWYER)
					.approvalStatus(ApprovalStatus.APPROVED).build();

			Lawyer aiLawyer = Lawyer.builder()
					.name("로인컴 AI 어시스턴트")
					.description("""
									안녕하세요! 저는 로인컴의 AI 어시스턴트에요.
							
									로인컴에서 25년 3월 부터 재직 중인 로봇이에요. 🤖
							
									\s
							
									커뮤니티 질문&답변에서, 유사한 질문자님의 질문과 유사한 판결을 찾아 궁금한 내용을 답변해드리는 역할을 하고 있어요.
									\s
									유사한 판결을 못찾으면 웹 검색을 해서 답변도 한답니다.
							
									제가 드리는 답변은 항상 참고용이니 주의 해주세요!
									어떤 의견과 피드백도 환영입니다! 🤟🏻
							""")
					.phoneNumber("054-478-7540")
					.officeInfo(OfficeInfo.builder().address("경상북도 구미시 대학로 61 - 디지털관 D337호")
							.name("금오공과대학교 컴퓨터 공학부 소프트웨어 공학전공 신호처리 및 지능형 네트워크 연구실")
							.phoneNumber("054-478-7540")
							.build())
					.build();
			aiLawyer.addAccount(aiAccount);

			List<LegalSpecialityInfo> list = Arrays.stream(LegalSpeciality.values()).map(spec -> {
				LegalSpecialityInfo legalSpecialityInfo = new LegalSpecialityInfo();
				legalSpecialityInfo.setLegalSpeciality(spec);
				legalSpecialityInfo.setLawyer(aiLawyer);
				return legalSpecialityInfo;
			}).toList();

			aiLawyer.setLegalSpecialities(list);
			List<Career> careers = List.of(Career.builder().content("-").lawyer(aiLawyer).build());
			aiLawyer.setCareers(careers);
			aiLawyer.setEducations(List.of(Education.builder().content("국립금오공과대학교 컴퓨터공학부 소프트웨어 공학 전공").lawyer(aiLawyer).build()));
			lawyerRepository.save(aiLawyer);
		}
	}
}
