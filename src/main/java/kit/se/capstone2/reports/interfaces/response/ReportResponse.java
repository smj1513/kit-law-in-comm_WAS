package kit.se.capstone2.reports.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReportResponse {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AnswerReportRes{
		private Long reportId;
		private Long answerId;
		private String answerTitle;
		private String reportContent;
		private String createdAt;
		private String reporterName;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class QuestionReportRes {
		private Long reportId;
		private Long questionId;
		private String reportContent;
		private LocalDateTime createdAt;
		private String reporterName;
	}
}
