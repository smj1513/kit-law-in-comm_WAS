package kit.se.capstone2.posts.question.interfaces.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuestionResponse
{
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PostQuestion {

		public PostQuestion(Question question, Long reportCount){
			this.questionId = question.getId();
			this.title = question.getTitle();
			this.content = question.getContent();
			this.authorName = question.getIsAnonymous() ? null : question.getAuthor().getName();
			this.authorId = question.getIsAnonymous() ? null : question.getAuthor().getAccount().getUsername();
			this.createdAt = question.getCreatedAt();
			this.updatedAt = question.getUpdatedAt();
			this.legalSpeciality = question.getLegalSpeciality();
			this.firstOccurrenceDate = question.getFirstOccurrenceDate();
			this.viewCount = question.getViewCount();
			this.isAnonymous = question.getIsAnonymous();
			this.reportCount = reportCount;
		}

		private Long questionId;

		private String title;

		private String content;

		@Schema(description = "작성자 이름, 익명일 경우 null")
		private String authorName;

		@Schema(description = "작성자 ID, 익명일 경우 null")
		private String authorId;

		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		@Schema(description = "법률 분야")
		private LegalSpeciality legalSpeciality;

		@Schema(description = "최초 사건 발생일")
		private LocalDate firstOccurrenceDate;

		@Schema(description = "조회수")
		private long viewCount;

		@Schema(description = "익명 여부")
		private boolean isAnonymous;

		@Schema(description = "신고 횟수")
		private Long reportCount;

		public static PostQuestion from(Question question, Long reportCount) {
			Boolean isAnonymous = question.getIsAnonymous();
			return PostQuestion.builder()
					.questionId(question.getId())
					.title(question.getTitle())
					.content(question.getContent())
					.authorName(isAnonymous ? null :question.getAuthor().getName())
					.authorId(isAnonymous ? null :question.getAuthor().getAccount().getUsername())
					.createdAt(question.getCreatedAt())
					.updatedAt(question.getUpdatedAt())
					.legalSpeciality(question.getLegalSpeciality())
					.firstOccurrenceDate(question.getFirstOccurrenceDate())
					.viewCount(question.getViewCount())
					.isAnonymous(isAnonymous)
					.reportCount(reportCount)
					.build();
		}
	}

}
