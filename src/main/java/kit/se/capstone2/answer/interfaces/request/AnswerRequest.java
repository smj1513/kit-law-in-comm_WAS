package kit.se.capstone2.answer.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AnswerRequest {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AnswerPost{
		private String content;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AnswerPut {
		private String content       ;
	}
}
