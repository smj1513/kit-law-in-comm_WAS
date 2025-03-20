package kit.se.capstone2.posts.question.interfaces.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class QuestionRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Create {
		private String title;
		private LegalSpeciality legalSpeciality;
		private String content;
		private LocalDate firstOccurrenceDate;
		@Schema(description = "익명 여부")
		private boolean isAnonymous;
	}
}
