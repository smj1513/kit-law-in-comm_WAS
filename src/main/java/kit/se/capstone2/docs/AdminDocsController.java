package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.interfaces.request.AdminRequest;
import kit.se.capstone2.user.interfaces.response.AdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "관리자 페이지 API", description = "관리자 API")
public interface AdminDocsController {

	@Operation(summary = "신고된 답변글 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<AdminResponse.ReportedAnswer>> getReportedPosts(@RequestParam int page, @RequestParam int size);

	@Operation(summary = "신고된 질문글 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<AdminResponse.ReportedQuestion>> getReportedQuestions(
			@RequestParam int page, @RequestParam int size
	);

	@Operation(summary = "변호사 승인 대기 조회")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<AdminResponse.ConfirmationLawyer>> getConfirmationLawyers(
			@RequestParam int page, @RequestParam int size
	);

	@Operation(summary = "변호사 승인/거절")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<AdminResponse.ConfirmationLawyer> confirmLawyer(
			@RequestBody AdminRequest.Confirmation request
	);
}
