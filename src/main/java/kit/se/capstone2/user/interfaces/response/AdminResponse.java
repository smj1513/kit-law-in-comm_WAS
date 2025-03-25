package kit.se.capstone2.user.interfaces.response;

import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.model.lawyer.Career;
import kit.se.capstone2.user.domain.model.lawyer.Education;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
		private String nickname;

		private String createdAt;
		private String updatedAt;

		private int ViewCount;
		private int reportCount;
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
		private String nickname;

		private String createdAt;
		private String updatedAt;

		private int ViewCount;
		private int reportCount;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class ConfirmationLawyer {

		private Long lawyerId;

		private String name;
		private String phoneNumber;
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

		private ApprovalStatus approvalStatus;

		private FileResponse licenseImageInfo;

		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

		public static ConfirmationLawyerDetails from(Lawyer lawyer){
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
					.build();
		}
	}
}
