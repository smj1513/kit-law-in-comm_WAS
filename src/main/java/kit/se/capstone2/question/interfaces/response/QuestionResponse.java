package kit.se.capstone2.question.interfaces.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.user.domain.enums.LegalSpecialty;
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
		private LegalSpecialty legalSpecialty;

		@Schema(description = "최초 사건 발생일")
		private LocalDate firstOccurrenceDate;

		@Schema(description = "조회수")
		private int viewCount;

		@Schema(description = "익명 여부")
		private boolean isAnonymous;

		@Schema(description = "신고 횟수")
		private int reportCount;
	}

}
