package kit.se.capstone2.user.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.answer.domain.repository.AnswerRepository;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.reports.domain.repository.AnswerReportRepository;
import kit.se.capstone2.reports.domain.repository.QuestionReportRepository;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.model.lawyer.LegalSpecialityInfo;
import kit.se.capstone2.user.domain.repository.LawyerRepository;
import kit.se.capstone2.user.interfaces.request.AdminRequest;
import kit.se.capstone2.user.interfaces.response.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAppService {
	private final LawyerRepository lawyerRepository;
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	private final QuestionReportRepository questionReportRepository;
	private final AnswerReportRepository answerReportRepository;


	public Page<AdminResponse.ConfirmationLawyer> getConfirmationLawyers(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Lawyer> result = lawyerRepository.findAllByApprovalStatus(ApprovalStatus.WAITING, pageRequest);
		return result.map(l -> AdminResponse.ConfirmationLawyer.builder()
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
				.birthDate(lawyer.getBirthDate())
				.legalSpeciality(lawyer.getLegalSpecialities().stream().map(LegalSpecialityInfo::getLegalSpeciality).toList())
				.description(lawyer.getDescription())
				.phoneNumber(lawyer.getPhoneNumber())
				.approvalStatus(account.getApprovalStatus())
				.build();
	}

	public AdminResponse.ConfirmationLawyerDetails getConfirmationLawyerDetails(Long id) {
		Lawyer lawyer = lawyerRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 변호사가 존재하지않습니다."));
		return AdminResponse.ConfirmationLawyerDetails.from(lawyer);
	}

	public Page<AdminResponse.ReportedAnswer> retrieveReportedAnswers(int threshold, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("reportsCount")));
		Page<Answer> answers = answerRepository.findByReportsCount(threshold, pageRequest);
		return answers.map(AdminResponse.ReportedAnswer::from);
	}

	public Page<AdminResponse.ReportedQuestion> retrieveReportedQuestion(int threshold, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("reportsCount")));
		Page<Question> questions = questionRepository.findByReportsCount(threshold, pageRequest);
		return questions.map(AdminResponse.ReportedQuestion::from);
	}

	public AdminResponse.RemoveQuestions removeQuestions(AdminRequest.RemoveQuestionsReq request) {
		answerReportRepository.deleteAllByAnswerIds(request.getQuestionIds());
		answerRepository.deleteAllByQuestionId(request.getQuestionIds());
		questionReportRepository.deleteAllByQuestionIds(request.getQuestionIds());
		int count = questionRepository.deleteAllById(request.getQuestionIds());
		return AdminResponse.RemoveQuestions.builder().removedCount(count).build();
	}

	public AdminResponse.RemoveAnswers removeAnswers(AdminRequest.RemoveAnswersReq request) {
		answerReportRepository.deleteAllByAnswerIds(request.getAnswerIds());
		int count = answerRepository.deleteAllById(request.getAnswerIds());
		return AdminResponse.RemoveAnswers.builder().removedCount(count).build();
	}
}
