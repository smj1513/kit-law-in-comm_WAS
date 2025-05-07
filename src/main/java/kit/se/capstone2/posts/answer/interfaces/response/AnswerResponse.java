package kit.se.capstone2.posts.answer.interfaces.response;

import kit.se.capstone2.posts.answer.domain.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class AnswerResponse {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GetAnswer {
		private Long answerId;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private int reportCount;
		private String authorName;
		private Long authorId;
		private boolean isAuthor;

		public static GetAnswer from(Answer answer) {
			return answer == null ? null : GetAnswer.builder()
					.answerId(answer.getId())
					.content(answer.getContent())
					.createdAt(answer.getCreatedAt())
					.updatedAt(answer.getUpdatedAt())
					.reportCount(answer.getReportsCount())
					.authorName(answer.getAuthor().getName())
					.authorId(answer.getAuthor().getId())
					.build();
		}

	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PostAnswer {
		private Long answerId;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private int reportCount;
		private String authorName;
		private Long authorId;

		public static PostAnswer from(Answer answer) {
			return answer == null ? null : PostAnswer.builder()
					.answerId(answer.getId())
					.content(answer.getContent())
					.createdAt(answer.getCreatedAt())
					.updatedAt(answer.getUpdatedAt())
					.reportCount(answer.getReportsCount())
					.authorName(answer.getAuthor().getName())
					.authorId(answer.getAuthor().getId())
					.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PutAnswer {
		private Long answerId;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private int reportCount;
		private String authorName;
		private Long authorId;

		public static PutAnswer from(Answer answer) {
			return answer == null ? null : PutAnswer.builder()
					.answerId(answer.getId())
					.content(answer.getContent())
					.createdAt(answer.getCreatedAt())
					.updatedAt(answer.getUpdatedAt())
					.reportCount(answer.getReportsCount())
					.authorName(answer.getAuthor().getName())
					.authorId(answer.getAuthor().getId())
					.build();
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class DeleteAnswer {
		private Long answerId;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private int reportCount;


	}
}
