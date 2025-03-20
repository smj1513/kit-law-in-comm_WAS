package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.auth.interfaces.request.TokenRequest;
import kit.se.capstone2.auth.jwt.model.JwtToken;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증/인가 관련 API", description = "인증 관련 API")
public interface AuthDocsController {

	@Operation(summary = "JWT 토큰 갱신", description = "JWT 토큰을 갱신합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "JWT 토큰 갱신 성공"),
			@ApiResponse(responseCode = "4010608", description = "JWT 토큰 갱신 실패 - 리프래시 토큰 만료시"),
			@ApiResponse(responseCode = "4000604", description = "JWT 토큰 갱신 실패 - 토큰 검증 실패")
	}
	)
	CommonResponse<JwtToken> refresh(TokenRequest.Refresh jwtToken);
}
