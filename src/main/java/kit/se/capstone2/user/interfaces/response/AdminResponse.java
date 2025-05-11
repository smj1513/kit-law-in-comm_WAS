package kit.se.capstone2.user.interfaces.response;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.lawyer.Career;
import kit.se.capstone2.user.domain.model.lawyer.Education;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.interfaces.OfficeInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AdminResponse {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ReportedAnswer {
		private Long postId;
		private Long authorId;

		private String title;
		private String content;

		private String createdAt;
		private String updatedAt;

		private int ViewCount;
		private int reportCount;

		public static ReportedAnswer from(Answer answer) {
			return answer == null ? null :
					ReportedAnswer.builder()
							.postId(answer.getId())
							.authorId(answer.getAuthor().getId())
							.title(answer.getQuestion().getTitle())
							.content(answer.getContent())
							.createdAt(answer.getCreatedAt().toString())
							.updatedAt(answer.getUpdatedAt().toString())
							.ViewCount(answer.getQuestion().getViewCount())
							.reportCount(answer.getReportsCount())
							.build();
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ReportedQuestion {
		private Long postId;
		private Long authorId;

		private String title;
		private String content;

		private LegalSpeciality legalSpeciality;

		private String createdAt;
		private String updatedAt;

		private int viewCount;
		private int reportCount;

		public static ReportedQuestion from(Question question) {
			return question == null ? null : ReportedQuestion.builder()
					.postId(question.getId())
					.authorId(question.getAuthor().getId())
					.title(question.getTitle())
					.content(question.getContent())
					.createdAt(question.getCreatedAt().toString())
					.updatedAt(question.getUpdatedAt().toString())
					.viewCount(question.getViewCount())
					.reportCount(question.getReportsCount())
					.legalSpeciality(question.getLegalSpeciality())
					.build();
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ConfirmationLawyer {

		private Long lawyerId;

		private String name;
		private String phoneNumber;
		private LocalDate birthDate;
		@Builder.Default
		private List<LegalSpeciality> legalSpeciality = new ArrayList<>();
		private String description;

		private ApprovalStatus approvalStatus;

	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ConfirmationLawyerDetails {

		private Long lawyerId;

		private String name;
		private String phoneNumber;
		private String description;

		@Builder.Default
		private List<String> career = new ArrayList<>();

		@Builder.Default
		private List<String> educations = new ArrayList<>();

		private OfficeInfoDTO officeInfo;

		private ApprovalStatus approvalStatus;

		private FileResponse licenseImageInfo;

		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public static ConfirmationLawyerDetails from(Lawyer lawyer) {
			Account account = lawyer.getAccount();

			return ConfirmationLawyerDetails.builder()
					.career(lawyer.getCareers().stream().map(Career::getContent).toList())
					.approvalStatus(account.getApprovalStatus())
					.lawyerId(lawyer.getId())
					.createdAt(account.getCreatedAt())
					.updatedAt(account.getUpdatedAt())
					.name(lawyer.getName())
					.phoneNumber(lawyer.getPhoneNumber())
					.description(lawyer.getDescription())
					.educations(lawyer.getEducations().stream().map(Education::getContent).toList())
					.licenseImageInfo(FileResponse.from(lawyer.getLicense()))
					.officeInfo(OfficeInfoDTO.from(lawyer.getOfficeInfo()))
					.build();
		}
	}
}
