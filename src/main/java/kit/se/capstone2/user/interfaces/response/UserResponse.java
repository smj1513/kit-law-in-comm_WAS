package kit.se.capstone2.user.interfaces.response;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.file.interfaces.response.FileResponse;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.BaseUser;
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
		private String username;
		private Role role;
		private ApprovalStatus approvalStatus;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LawyerRes {
		private String username;
		private Role role;
		private ApprovalStatus approvalStatus;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserInfo {
		private String name;
		private String nickname;
		private FileResponse profileImage;

		public static UserInfo from(BaseUser user){
			return UserInfo.builder()
					.name(user.isLawyer() || user.isAdmin() ? user.getName() : null)
					.nickname(user.isClient() ? user.getNickname() : null)
					.profileImage(FileResponse.from(user.getProfileImage()))
					.build();
		}
	}
}
