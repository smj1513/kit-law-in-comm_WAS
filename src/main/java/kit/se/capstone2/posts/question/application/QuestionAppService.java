package kit.se.capstone2.posts.question.application;

import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.posts.question.domain.repository.QuestionRepository;
import kit.se.capstone2.posts.question.interfaces.request.QuestionRequest;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.reports.domain.repository.QuestionReportRepository;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionAppService {
	private final QuestionRepository questionRepository;
	private final QuestionReportRepository reportRepository;

	public Page<QuestionResponse.PostQuestion> getQuestionByLegalSpeciality(LegalSpeciality legalSpeciality, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Question> questions = questionRepository.findByLegalSpeciality(legalSpeciality, pageRequest);

		return questions.map(question -> {
			Boolean isAnonymous = question.getIsAnonymous();
			return QuestionResponse.PostQuestion.builder()
					.questionId(question.getId())
					.title(question.getTitle())
					.content(question.getContent())
					.legalSpeciality(question.getLegalSpeciality())
					.createdAt(question.getCreatedAt())
					.updatedAt(question.getUpdatedAt())
					.reportCount(reportRepository.countByQuestion(question))
					.authorId(isAnonymous ? null : question.getAuthor().getAccount().getUsername())
					.authorName(isAnonymous ? null : question.getAuthor().getName())
					.firstOccurrenceDate(question.getFirstOccurrenceDate())
					.viewCount(question.getViewCount())
					.isAnonymous(isAnonymous)
					.build();
		});
	}


	public Page<QuestionResponse.PostQuestion> getQuestions(int page, int size) {
		return null;
	}

	public QuestionResponse.PostQuestion getQuestionById(Long id) {
		return null;
	}

	public QuestionResponse.PostQuestion createQuestion(QuestionRequest.Create request) {
		return null;
	}

	public QuestionResponse.PostQuestion deleteQuestion(Long id) {
		return null;
	}

	public QuestionResponse.PostQuestion updateQuestion(Long id, QuestionRequest.Create request) {
		return null;
	}

	public Page<QuestionResponse.PostQuestion> searchQuestions(String keyword, int page, int size) {
		return null;
	}
}
