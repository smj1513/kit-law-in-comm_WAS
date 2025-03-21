package kit.se.capstone2.user.interfaces.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JoinGeneralUser {
		@Schema(description = "로그인 할때 사용할 아이디", example = "user1")
		private String username;
		private String name;
		private String nickname;
		private String password;
		private LocalDate birthDate;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JoinLawyer {
		@Schema(description = "로그인 할때 사용할 아이디", example = "lawyer1")
		private String username;
		private String password;
		private String name;
		private String phoneNumber;
		@Builder.Default
		private List<LegalSpeciality> legalSpecialties = new ArrayList<>();
		@Builder.Default
		private List<String> careers = new ArrayList<>();
		@Builder.Default
		private List<String> educations = new ArrayList<>();

		private OfficeInfo officeInfo;
	}
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OfficeInfo{
		private String officeName;
		private String officePhoneNumber;
		private String officeAddress;
	}
}
