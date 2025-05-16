package kit.se.capstone2.user.interfaces.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.List;

public class AdminRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Confirmation{
		@NotNull
		private Boolean isApprove;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RemoveQuestionsReq {
		private List<Long> questionIds;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class RemoveAnswersReq {
		private List<Long> answerIds;
	}
}
