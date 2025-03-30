package kit.se.capstone2.reports.interfaces.response;

import kit.se.capstone2.reports.domain.model.AnswerReport;
import kit.se.capstone2.reports.domain.model.QuestionReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReportResponse {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AnswerReportDetails{
		private Long reportId;
		private String reportContent;
		private LocalDateTime createdAt;
		private String reporterName;

		public static AnswerReportDetails from(AnswerReport answerReport) {
			return AnswerReportDetails.builder()
					.reportId(answerReport.getId())
					.reportContent(answerReport.getReason())
					.createdAt(answerReport.getCreatedAt())
					.reporterName(answerReport.getReporter().getName())
					.build();
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AnswerReportRes{
		private Long reportId;
		private Long answerId;
		private String answerTitle;
		private String reportContent;
		private LocalDateTime createdAt;
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

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class QuestionReportDetails {
		private Long reportId;
		private String reportContent;
		private LocalDateTime createdAt;
		private String reporterName;

		public static QuestionReportDetails from(QuestionReport questionReport) {
			return QuestionReportDetails.builder()
					.reportId(questionReport.getId())
					.reportContent(questionReport.getReason())
					.createdAt(questionReport.getCreatedAt())
					.reporterName(questionReport.getReporter().getName())
					.build();
		}
	}
}
