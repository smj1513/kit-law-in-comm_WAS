package kit.se.capstone2.question.interfaces.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class QuestionResponse
{
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Post {
		private Long questionId;
		private String title;
		private String content;
		@Schema(description = "최초 사건 발생일")
		private LocalDate firstOccurrenceDate;
		private int viewCount;
		private boolean isAnonymous;
	}

}
