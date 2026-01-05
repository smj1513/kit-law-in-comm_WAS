package kit.se.capstone2.auth.interfaces.controller;

import kit.se.capstone2.auth.application.TokenService;
import kit.se.capstone2.auth.interfaces.request.TokenRequest;
import kit.se.capstone2.auth.interfaces.response.LoginResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.AuthDocsController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthDocsController {

	private final TokenService tokenService;

	@PostMapping("/token/refresh")
	public CommonResponse<LoginResponse> refresh(
			@RequestBody TokenRequest.Refresh jwtToken
	) {
		return CommonResponse.success(SuccessCode.CREATED, tokenService.refresh(jwtToken));
	}

	@PostMapping("/logout")
	public CommonResponse<?> logout(@RequestBody TokenRequest.Logout request) {
		tokenService.logout(request);
		return CommonResponse.success(SuccessCode.OK, null);
	}
}
