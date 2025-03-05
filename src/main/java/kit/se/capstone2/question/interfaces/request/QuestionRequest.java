package kit.se.capstone2.question.interfaces.request;

import kit.se.capstone2.user.domain.enums.LegalSpecialty;
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
	public static class Question{
		private String title;
		private LegalSpecialty legalSpecialty;
		private String content;
		private LocalDate firstOccurrenceDate;
	}
}
