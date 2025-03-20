package kit.se.capstone2.user.interfaces.request;


import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MyPageRequest {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UpdateGeneralInfo {
		private String name;
		private String nickname;
		private String birth;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UpdateLawyerInfo {
		private String name;
		private String phoneNumber;
		private String description;

		private OfficeInfo officeInfo;

		@Builder.Default
		private List<String> careers = new ArrayList<>();

		@Builder.Default
		private List<String> educations = new ArrayList<>();

		@Builder.Default
		private List<LegalSpeciality> legalSpecialties = new ArrayList<>();
	}

	@Data
	@NoArgsConstructor
	@Builder
	@AllArgsConstructor
	public static class OfficeInfo{
		private String officeName;
		private String officePhoneNumber;
		private String officeAddress;
	}
}
