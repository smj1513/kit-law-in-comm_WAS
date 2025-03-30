package kit.se.capstone2.user.interfaces.request;


import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.interfaces.OfficeInfoDTO;
import lombok.*;

import java.time.LocalDate;
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
		private LocalDate birth;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UpdateLawyerInfo {
		private String phoneNumber;
		private String description;

		private OfficeInfoDTO officeInfo;

		@Builder.Default
		private List<String> careers = new ArrayList<>();

		@Builder.Default
		private List<String> educations = new ArrayList<>();

		@Builder.Default
		private List<LegalSpeciality> legalSpecialities = new ArrayList<>();
	}

}
