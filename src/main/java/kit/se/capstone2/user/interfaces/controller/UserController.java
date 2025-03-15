package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.UserDocsController;
import kit.se.capstone2.user.domain.enums.LegalSpecialty;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserDocsController {

	@PostMapping("/join/general")
	public CommonResponse<UserResponse.General> joinGeneral(@RequestBody UserRequest.JoinGeneralUser request) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PostMapping(path = "/join/lawyer", consumes = {"multipart/form-data"})
	public CommonResponse<UserResponse.Lawyer> joinLawyer(
			@RequestPart("data") UserRequest.JoinLawyer data,
			@RequestPart MultipartFile licenseImage) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/legal-specialists")
	public CommonResponse<List<UserResponse.LegalSpecialtyInfo>> getLegalSpecialists() {
		return CommonResponse.success(SuccessCode.OK, Stream.of(LegalSpecialty.values()).map(UserResponse.LegalSpecialtyInfo::from).toList());
	}
}
