package kit.se.capstone2.user.application;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.model.ClientUser;
import kit.se.capstone2.user.domain.model.lawyer.*;
import kit.se.capstone2.user.domain.repository.BaseUserRepository;
import kit.se.capstone2.user.domain.repository.ClientUserRepository;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import kit.se.capstone2.user.domain.service.LawyerService;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAppService {
	private final ClientUserRepository clientUserRepository;
	private final LawyerRepository lawyerRepository;
	private final BaseUserRepository baseUserRepository;
	private final LawyerService lawyerService;

	public UserResponse.General joinGeneralUser(UserRequest.JoinGeneralUser request) {
		if(clientUserRepository.existsByNickname(request.getNickname())){
			throw new BusinessLogicException(ErrorCode.ENTITY_DUPLICATED, "닉네임이 중복되었습니다.");
		}
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

	public UserResponse.LawyerRes joinLawyer(UserRequest.JoinLawyer request, MultipartFile licenseImage) {
		Account account = Account.builder()
				.username(request.getUsername())
				.password(request.getPassword())
				.role(Role.ROLE_LAWYER)
				.approvalStatus(ApprovalStatus.WAITING)
				.build();

		OfficeInfo officeInfo = request.getOfficeInfo().toEntity();

		Lawyer lawyer = lawyerService.createLawyerLicense(Lawyer.builder()
						.name(request.getName())
						.phoneNumber(request.getPhoneNumber())
						.description(request.getDescription())
						.birthDate(request.getBirthDate())
						.build(),
				licenseImage);

		lawyer.addOfficeInfo(officeInfo);

		List<LegalSpecialityInfo> legalSpecialityInfos = request.getLegalSpecialties().stream().map(sepc -> {
			LegalSpecialityInfo legalSpecialityInfo = new LegalSpecialityInfo();
			legalSpecialityInfo.setLegalSpeciality(sepc);
			legalSpecialityInfo.setLawyer(lawyer);
			return legalSpecialityInfo;
		}).toList();

		List<Education> educations = request.getEducations().stream().map(education -> {
			Education edu = new Education();
			edu.setLawyer(lawyer);
			edu.setContent(education);
			return edu;
		}).toList();

		List<Career> careers = request.getCareers().stream().map(career -> {
			Career care = new Career();
			care.setLawyer(lawyer);
			care.setContent(career);
			return care;
		}).toList();

		lawyer.setLegalSpecialities(legalSpecialityInfos);
		lawyer.setEducations(educations);
		lawyer.addAccount(account);
		lawyer.setCareers(careers);

		Lawyer save = lawyerRepository.save(lawyer);

		return UserResponse.LawyerRes.builder()
				.username(save.getAccount().getUsername())
				.role(save.getAccount().getRole())
				.approvalStatus(save.getAccount().getApprovalStatus())
				.build();
	}

	@Transactional(readOnly = true)
	public UserResponse.UserInfo getUserInfo(Long id) {
		BaseUser baseUser = baseUserRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 사용자가 존재하지 않습니다."));
		return UserResponse.UserInfo.from(baseUser);
	}

	public Boolean checkNicknameDuplication(String nickname) {
		return clientUserRepository.existsByNickname(nickname);
	}
}
