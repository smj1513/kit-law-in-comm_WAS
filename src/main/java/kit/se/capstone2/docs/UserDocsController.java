package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.domain.enums.LegalSpecialty;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

@Tag(name = "사용자 관련 API", description = "사용자 관련 API 문서")
public interface UserDocsController {

	@Operation(summary = "일반 사용자 회원 가입", description = "일반 사용자로 가입한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<UserResponse.General> joinGeneral(@RequestBody UserRequest.JoinGeneralUser request);

	@Operation(summary = "변호사 회원 가입", description = "변호사로 가입한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<UserResponse.Lawyer> joinLawyer(
			@RequestPart("data") UserRequest.JoinLawyer data,
			@RequestPart MultipartFile licenseImage);

	@Operation(summary = "변호사의 전문 분야 목록 조회", description = "전문 분야 목록을 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<List<UserResponse.LegalSpecialtyInfo>> getLegalSpecialists();
}
