package kit.se.capstone2.posts.question.application;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.posts.question.interfaces.request.QuestionRequest;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.reports.domain.repository.QuestionReportRepository;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.BaseUser;
import kit.se.capstone2.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionAppService {
	private final QuestionRepository questionRepository;
	private final SecurityUtils securityUtils;
	private final UserService userService;

	public Page<QuestionResponse.PostQuestion> getQuestionByLegalSpeciality(LegalSpeciality legalSpeciality, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
		Page<Question> result = questionRepository.findByLegalSpeciality(legalSpeciality, pageRequest);

		return result.map(QuestionResponse.PostQuestion::from);
	}


	public Page<QuestionResponse.PostQuestion> getQuestions(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
		Page<Question> result = questionRepository.findAll(pageRequest);
		return result.map(QuestionResponse.PostQuestion::from);
	}

	public QuestionResponse.PostQuestion retrievalQuestionDetails(Long id) {
		Question question = questionRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 질문이 존재하지 않습니다."));
		question.addViewCount();
		return QuestionResponse.PostQuestion.from(question);
	}

	public QuestionResponse.PostQuestion createQuestion(QuestionRequest.Create request) {
		Account currentUser = securityUtils.getCurrentUser();
		BaseUser user = currentUser.getUser();
		Question question = Question.builder()
				.title(request.getTitle())
				.content(request.getContent())
				.legalSpeciality(request.getLegalSpeciality())
				.firstOccurrenceDate(request.getFirstOccurrenceDate())
				.isAnonymous(request.isAnonymous())
				.build();

		//TODO : RAG 시스템과 연동 비동기 요청 처리

		user.addQuestion(question);
		Question save = questionRepository.save(question);
		return QuestionResponse.PostQuestion.from(save);
	}

	public QuestionResponse.PostQuestion deleteQuestion(Long id) {
		Account currentUser = securityUtils.getCurrentUser();
		BaseUser user = currentUser.getUser();
		Question question = questionRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 질문이 존재하지 않습니다."));
		BaseUser author = question.getAuthor();
		userService.validateRemoveAuthority(user, author);
		questionRepository.delete(question);
		return QuestionResponse.PostQuestion
				.from(question);
	}

	public QuestionResponse.PostQuestion updateQuestion(Long id, QuestionRequest.Create request) {
		Account currentUser = securityUtils.getCurrentUser();
		BaseUser user = currentUser.getUser();
		Question question = questionRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND_ENTITY, "해당하는 질문이 존재하지 않습니다."));
		BaseUser author = question.getAuthor();
		userService.validateModifyAuthority(user, author);
		question.setContent(request.getContent());
		question.setTitle(request.getTitle());
		question.setLegalSpeciality(request.getLegalSpeciality());
		question.setFirstOccurrenceDate(request.getFirstOccurrenceDate());
		Question save = questionRepository.save(question);
		return QuestionResponse.PostQuestion.from(save);
	}


	public Page<QuestionResponse.PostQuestion> searchQuestions(String keyword, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
		String searchKeyword = "%" + keyword + "%";
		Page<Question> result = questionRepository.findByKeyword(searchKeyword, pageRequest);
		return result.map(QuestionResponse.PostQuestion::from);
	}
}
