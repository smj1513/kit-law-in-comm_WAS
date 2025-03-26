package kit.se.capstone2.reports.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReportRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AnswerReportReq{
		private String reason;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class QuestionReportReq{
		private String reason;
	}
}
