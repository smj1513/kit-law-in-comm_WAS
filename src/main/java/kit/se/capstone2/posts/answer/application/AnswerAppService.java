package kit.se.capstone2.posts.answer.application;

import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.answer.domain.repository.AnswerRepository;
import kit.se.capstone2.posts.answer.interfaces.request.AnswerRequest;
import kit.se.capstone2.posts.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.reports.domain.repository.AnswerReportRepository;
import kit.se.capstone2.user.application.UserAppService;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerAppService {
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;
	private final UserService userService;
	private final SecurityUtils securityUtils;

	public Page<AnswerResponse.GetAnswer> retrieveAnswers(Long questionId, int page, int size) {

		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
		Page<Answer> result = answerRepository.findByQuestionId(questionId, pageRequest);
		return result.map(AnswerResponse.GetAnswer::from);

	}

	public AnswerResponse.PostAnswer createAnswer(Long questionId, AnswerRequest.AnswerPost request) {
		Lawyer currentLawyer = securityUtils.getCurrentLawyer();
		Question question = questionRepository.findById(questionId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 질문이 존재하지 않습니다."));

		Answer answer = Answer.builder()
				.content(request.getContent())
				.build();
		question.addAnswer(answer);
		currentLawyer.addAnswer(answer);
		Answer save = answerRepository.save(answer);
		return AnswerResponse.PostAnswer.from(save);
	}

	public AnswerResponse.PutAnswer updateAnswer(Long answerId, AnswerRequest.AnswerPut request) {
		Lawyer currentLawyer = securityUtils.getCurrentLawyer();
		Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 답변이 존재하지 않습니다."));
		Lawyer author = answer.getAuthor();

		userService.validateModifyAuthority(currentLawyer, author);
		answer.setContent(request.getContent());

		return AnswerResponse.PutAnswer.from(answer);
	}

	public AnswerResponse.DeleteAnswer removeAnswer(Long answerId) {
		Lawyer currentLawyer = securityUtils.getCurrentLawyer();
		Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 답변이 존재하지 않습니다."));
		Lawyer author = answer.getAuthor();

		userService.validateRemoveAuthority(currentLawyer, author);

		answerRepository.delete(answer);

		return AnswerResponse.DeleteAnswer.builder().answerId(answerId)
				.content(answer.getContent())
				.createdAt(answer.getCreatedAt())
				.updatedAt(answer.getUpdatedAt())
				.reportCount(answer.getReportsCount())
				.build();
	}
}
