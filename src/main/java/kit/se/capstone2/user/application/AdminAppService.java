package kit.se.capstone2.user.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import kit.se.capstone2.user.interfaces.request.AdminRequest;
import kit.se.capstone2.user.interfaces.response.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAppService {
	private final LawyerRepository lawyerRepository;


	public Page<AdminResponse.ConfirmationLawyer> getConfirmationLawyers(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Lawyer> result = lawyerRepository.findAllByApprovalStatus(ApprovalStatus.WAITING, pageRequest);
		return result.map(l-> AdminResponse.ConfirmationLawyer.builder()
				.lawyerId(l.getId())
				.name(l.getName())
				.description(l.getDescription())
				.phoneNumber(l.getPhoneNumber())
				.approvalStatus(l.getAccount().getApprovalStatus())
				.build());
	}

	public AdminResponse.ConfirmationLawyer confirmLawyer(long id, AdminRequest.Confirmation request) {
		Lawyer lawyer = lawyerRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 변호사가 존재하지않습니다."));
		Account account = lawyer.getAccount();
		account.changeApprovalStatus(request.getIsApprove());
		return AdminResponse.ConfirmationLawyer.builder()
				.lawyerId(lawyer.getId())
				.name(lawyer.getName())
				.description(lawyer.getDescription())
				.phoneNumber(lawyer.getPhoneNumber())
				.approvalStatus(account.getApprovalStatus())
				.build();
	}

	public AdminResponse.ConfirmationLawyerDetails getConfirmationLawyerDetails(Long id) {
		Lawyer lawyer = lawyerRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 변호사가 존재하지않습니다."));
		return AdminResponse.ConfirmationLawyerDetails.from(lawyer);
	}
}
