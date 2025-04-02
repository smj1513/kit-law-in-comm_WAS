package kit.se.capstone2.user.interfaces.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.domain.model.lawyer.OfficeInfo;
import kit.se.capstone2.user.interfaces.OfficeInfoDTO;
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
	@Valid
	public static class JoinGeneralUser {
		@Schema(description = "로그인 할때 사용할 아이디", example = "user1")
		@Pattern(regexp = "^[a-zA-Z0-9]{2,20}", message = "아이디는 2~20자의 영문 대소문자와 숫자로 이루어져야 합니다.")
		private String username;

		private String name;

		@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,8}", message = "닉네임은 2~8자의 한글 및 영문 대소문자와 숫자로 이루어져야 합니다.")
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
		private String description;
		private LocalDate birthDate;

		@Builder.Default
		private List<LegalSpeciality> legalSpecialties = new ArrayList<>();
		@Builder.Default
		private List<String> careers = new ArrayList<>();
		@Builder.Default
		private List<String> educations = new ArrayList<>();

		private OfficeInfoDTO officeInfo;
	}
}
