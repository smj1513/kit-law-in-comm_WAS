package kit.se.capstone2.user.interfaces.response;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserResponse  {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LegalSpecialtyInfo{
		private String legalSpecialtyName;
		private String legalSpecialtyDescription;

		public static LegalSpecialtyInfo from(LegalSpeciality legalSpeciality){
			return LegalSpecialtyInfo.builder()
					.legalSpecialtyName(legalSpeciality.name())
					.legalSpecialtyDescription(legalSpeciality.getDescription())
					.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class General {
		private String id;
		private Role role;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LawyerRes {
		private String id;
		private Role role;
		private ApprovalStatus approvalStatus;
	}
}
