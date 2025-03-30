package kit.se.capstone2.reports.interfaces.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

public class ReportRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AnswerReportReq{
		@Length(max = 500)
		private String reason;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class QuestionReportReq{
		@Length(max = 500)
		private String reason;
	}
}
