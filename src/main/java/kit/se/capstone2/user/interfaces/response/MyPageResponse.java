package kit.se.capstone2.user.interfaces.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.posts.answer.domain.model.Answer;
import kit.se.capstone2.posts.question.domain.model.Question;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.lawyer.Career;
import kit.se.capstone2.user.domain.model.lawyer.Education;
import kit.se.capstone2.user.domain.model.lawyer.Lawyer;
import kit.se.capstone2.user.domain.model.lawyer.LegalSpecialityInfo;
import kit.se.capstone2.user.interfaces.OfficeInfoDTO;
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
		private LocalDate birthDate;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LawyerInfo {
		private Long id;
		private String name;
		private String phoneNumber;
		private String description;
		private LocalDate birthDate;

		@Builder.Default
		private List<LegalSpeciality> legalSpecialties = new ArrayList<>();
		private List<String> careers;
		private List<String> educations;
		private OfficeInfoDTO officeInfo;

		public static LawyerInfo from(Lawyer lawyer){
			return LawyerInfo.builder()
					.id(lawyer.getId())
					.name(lawyer.getName())
					.phoneNumber(lawyer.getPhoneNumber())
					.description(lawyer.getDescription())
					.birthDate(lawyer.getBirthDate())
					.legalSpecialties(lawyer.getLegalSpecialities().stream().map(LegalSpecialityInfo::getLegalSpeciality).toList())
					.careers(lawyer.getCareers().stream().map(Career::getContent).toList())
					.educations(lawyer.getEducations().stream().map(Education::getContent).toList())
					.officeInfo(lawyer.getOfficeInfo() == null ? null : OfficeInfoDTO.from(lawyer.getOfficeInfo()))
					.build();
		}
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
		private LegalSpeciality legalSpeciality;
		private int reportCount;
		private int viewCount;

		public static QuestionInfo from(Question question) {
			return QuestionInfo.builder()
					.questionId(question.getId())
					.title(question.getTitle())
					.content(question.getContent())
					.createdAt(question.getCreatedAt())
					.updatedAt(question.getUpdatedAt())
					.reportCount(question.getReportsCount())
					.legalSpeciality(question.getLegalSpeciality())
					.viewCount(question.getViewCount())
					.build();
		}
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
		private String questionTitle;
		private LegalSpeciality questionLegalSpeciality;
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;
		private int reportCount;

		public static AnswerInfo from(Answer answer) {
			return AnswerInfo.builder()
					.questionId(answer.getQuestion().getId())
					.questionTitle(answer.getQuestion().getTitle())
					.questionLegalSpeciality(answer.getQuestion().getLegalSpeciality())
					.answerId(answer.getId())
					.content(answer.getContent())
					.createdAt(answer.getCreatedAt())
					.updatedAt(answer.getUpdatedAt())
					.reportCount(answer.getReportsCount())
					.build();
		}
	}

}
