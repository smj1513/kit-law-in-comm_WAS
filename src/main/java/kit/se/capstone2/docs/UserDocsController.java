package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "사용자 관련 API", description = "사용자 관련 API 문서")
public interface UserDocsController {

	@Operation(summary = "일반 사용자 회원 가입", description = "일반 사용자로 가입한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<UserResponse.General> joinGeneral(UserRequest.JoinGeneralUser request);

	@RequestBody(content = @Content(
			encoding = @Encoding(name = "data", contentType = MediaType.APPLICATION_JSON_VALUE)))
	@Operation(summary = "변호사 회원 가입", description = "변호사로 가입한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<UserResponse.LawyerRes> joinLawyer(
			@RequestPart("data") UserRequest.JoinLawyer data,
			@Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
			MultipartFile licenseImage);

	@Operation(summary = "변호사의 전문 분야 목록 조회", description = "전문 분야 목록을 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<List<UserResponse.LegalSpecialtyInfo>> getLegalSpecialists();

	@Operation(summary = "사용자의 일반 정보 조회(이름, 닉네임, 프로필 이미지)", description = "사용자의 일반 정보를 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<UserResponse.UserInfo> getUserInfo(Long id);
}
