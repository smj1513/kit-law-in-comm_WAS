package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.posts.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.interfaces.request.MyPageRequest;
import kit.se.capstone2.user.interfaces.response.MyPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "MyPage API", description = "마이페이지 API")
public interface MyPageDocsController {

	@Operation(summary = "마이페이지 일반회원 정보 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<MyPageResponse.GeneralInfo> getGeneralInfo();

	@Operation(summary = "마이페이지 변호사 정보 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<MyPageResponse.LawyerInfo> getLawyerInfo();

	@Operation(summary = "마이페이지 일반회원 정보 수정")
	@ApiResponse(responseCode = "202", description = "성공")
	public CommonResponse<MyPageResponse.GeneralInfo> updateGeneralInfo(
			@RequestBody MyPageRequest.UpdateGeneralInfo request
	);

	@Operation(summary = "마이페이지 변호사 정보 수정")
	@ApiResponse(responseCode = "203", description = "성공")
	public CommonResponse<MyPageResponse.LawyerInfo> updateLawyerInfo(
			@RequestBody MyPageRequest.UpdateLawyerInfo request);

	@Operation(summary = "마이페이지 질문글 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<MyPageResponse.QuestionInfo>> getQuestionInfo(
			int page, int size
	);

	@Operation(summary = "마이페이지 변호사 답변글 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<AnswerResponse.GetAnswer>> getAnswerInfo(
			int page, int size
	);
}
