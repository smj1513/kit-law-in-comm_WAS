package kit.se.capstone2.user.interfaces.response;

import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
		//라이센스 이미지 조회 추가

	}
}
