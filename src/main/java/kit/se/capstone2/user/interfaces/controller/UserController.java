package kit.se.capstone2.user.interfaces.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.UserDocsController;
import kit.se.capstone2.user.application.UserAppService;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserDocsController {

	private final UserAppService appService;

	@PostMapping("/join/general")
	public CommonResponse<UserResponse.General> joinGeneral(@RequestBody UserRequest.JoinGeneralUser request) {
		return CommonResponse.success(SuccessCode.OK, appService.joinGeneralUser(request));
	}

	@PostMapping(path = "/join/lawyer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public CommonResponse<UserResponse.LawyerRes> joinLawyer(
			@RequestPart(value = "data", required = true) UserRequest.JoinLawyer data,
			@RequestPart(value = "licenseImage", required = true) MultipartFile licenseImage) {
		return CommonResponse.success(SuccessCode.OK, appService.joinLawyer(data, licenseImage));
	}

	@GetMapping("/join/id/dupe-check")
	public CommonResponse<UserResponse.IdDupCheck> checkIdDuplication(@RequestParam String id
	){
		return CommonResponse.success(SuccessCode.OK, appService.checkIdDupCheck(id));
	}


	@GetMapping("/join/nickname/dupe-check")
	public CommonResponse<Boolean> checkNicknameDuplication(
			@RequestParam
			String nickname) {
		return CommonResponse.success(SuccessCode.OK, appService.checkNicknameDuplication(nickname));
	}

	@GetMapping("/legal-speciality")
	public CommonResponse<List<UserResponse.LegalSpecialtyInfo>> getLegalSpecialists() {
		return CommonResponse.success(SuccessCode.OK, Stream.of(LegalSpeciality.values()).map(UserResponse.LegalSpecialtyInfo::from).toList());
	}

	@GetMapping("/info/{id}")
	public CommonResponse<UserResponse.UserInfo> getUserInfo(@PathVariable Long id) {
		return CommonResponse.success(SuccessCode.OK, appService.getUserInfo(id));
	}
}
