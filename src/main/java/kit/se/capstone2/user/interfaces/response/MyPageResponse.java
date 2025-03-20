package kit.se.capstone2.user.interfaces.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyPageResponse {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GeneralInfo {
		private String name;
		private String nickname;
		private LocalDate birth;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LawyerInfo {
		private String name;
		private String phoneNumber;
		private String description;

		@Builder.Default
		private List<LegalSpeciality> legalSpecialties = new ArrayList<>();
		private List<String> careers;
		private List<String> educations;
		private OfficeInfo officeInfo;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	private static class OfficeInfo {
		private String officeName;
		private String officePhoneNumber;
		private String officeAddress;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class QuestionInfo{
		@Schema(description = "질문글 ID - 클릭시 해당 질문글로 이동")
		private Long questionId;
		private String title;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private Long reportCount;
		private Long viewCount;
	}


	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AnswerInfo {
		@Schema(description = "질문글 ID - 클릭시 해당 질문글로 이동")
		private Long questionId;
		private Long answerId;
		private String content;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private Long reportCount;
	}

}
