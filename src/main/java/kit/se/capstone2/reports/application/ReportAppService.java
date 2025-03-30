package kit.se.capstone2.reports.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.answer.domain.repository.AnswerRepository;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.reports.domain.model.AnswerReport;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import kit.se.capstone2.reports.domain.repository.AnswerReportRepository;
import kit.se.capstone2.reports.domain.repository.QuestionReportRepository;
import kit.se.capstone2.reports.interfaces.request.ReportRequest;
import kit.se.capstone2.reports.interfaces.response.ReportResponse;
import kit.se.capstone2.user.domain.model.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportAppService {
	private final QuestionReportRepository questionReportRepository;
	private final QuestionRepository questionRepository;

	private final AnswerReportRepository answerReportRepository;
	private final SecurityUtils securityUtils;
	private final AnswerRepository answerRepository;

	public ReportResponse.QuestionReportRes reportQuestion(Long id, ReportRequest.QuestionReportReq request) {
		Account currentUser = securityUtils.getCurrentUser();
		BaseUser user = currentUser.getUser();
		Question question = questionRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 질문이 존재하지 않습니다."));

		if(questionReportRepository.existsByQuestionAndReporter(question, user)){
			throw new BusinessLogicException(ErrorCode.ALREADY_REPORTED, "이미 신고한 질문입니다.");
		}

		QuestionReport report = QuestionReport.builder()
				.reason(request.getReason())
				.build();
		question.addReport(report);
		user.addReport(report);
		QuestionReport save = questionReportRepository.save(report);

		return ReportResponse.QuestionReportRes
				.builder()
				.questionId(question.getId())
				.reportId(save.getId())
				.reporterName(user.getName())
				.createdAt(save.getCreatedAt())
				.build();
	}

	public ReportResponse.AnswerReportRes reportAnswer(Long id, ReportRequest.AnswerReportReq request) {
		BaseUser user = securityUtils.getCurrentUser().getUser();
		Answer answer = answerRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 답변이 존재하지 않습니다."));
		AnswerReport report = AnswerReport.builder()
				.reason(request.getReason())
				.build();

		if(answerReportRepository.existsByAnswerAndReporter(answer, user)){{
			throw new BusinessLogicException(ErrorCode.ALREADY_REPORTED, "이미 신고한 답변입니다.");}
		}

		answer.addReport(report);
		user.addReport(report);
		AnswerReport save = answerReportRepository.save(report);

		return ReportResponse.AnswerReportRes
				.builder()
				.answerId(answer.getId())
				.reportId(save.getId())
				.reporterName(user.getName())
				.createdAt(save.getCreatedAt())
				.build();
	}

	@Transactional(readOnly = true)
	public Page<ReportResponse.AnswerReportDetails> getAnswerReportDetails(Long answerId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<AnswerReport> answerReports = answerReportRepository.findByAnswerId(answerId, pageRequest);
		return answerReports.map(ReportResponse.AnswerReportDetails::from);
	}

	@Transactional(readOnly = true)
	public Page<ReportResponse.QuestionReportDetails> getQuestionReportDetails(Long questionId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<QuestionReport> questionReports = questionReportRepository.findByQuestionId(questionId, pageRequest);
		return questionReports.map(ReportResponse.QuestionReportDetails::from);
	}
}
