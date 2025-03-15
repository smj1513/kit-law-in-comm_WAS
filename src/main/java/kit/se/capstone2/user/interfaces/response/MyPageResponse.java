package kit.se.capstone2.user.interfaces.response;

import kit.se.capstone2.user.domain.enums.LegalSpecialty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
		private List<LegalSpecialty> legalSpecialties = new ArrayList<>();
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
}
