package kit.se.capstone2.answer.interfaces.response;

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
		private Long reportCount;
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
		private Long reportCount;
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
		private Long reportCount;
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
		private Long reportCount;
	}
}
