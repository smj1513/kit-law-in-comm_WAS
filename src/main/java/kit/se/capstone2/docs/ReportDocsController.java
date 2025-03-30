package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.reports.interfaces.request.ReportRequest;
import kit.se.capstone2.reports.interfaces.response.ReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "신고 API", description = "신고 API")
public interface ReportDocsController {

	@Operation(summary = "답변글 신고 상세 조회", description = "답변글의 신고 내역을 상세하게 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<ReportResponse.AnswerReportDetails>> getAnswerReportDetails(
			@PathVariable(name = "answer-id") Long answerId,
			@RequestParam int page, @RequestParam int size
	);

	@Operation(summary = "질문글 신고 상세 조회", description = "질문글의 신고 내역을 상세를 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<ReportResponse.QuestionReportDetails>> getQuestionReportDetails(
			Long questionId,
			int page, int size
	);


	@Operation(summary = "답변글 신고", description = "답변글을 신고한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<ReportResponse.AnswerReportRes> reportAnswer(
			@PathVariable(name = "id") Long id,
			@RequestBody ReportRequest.AnswerReportReq request
	);

	@Operation(summary = "질문글 신고", description = "질문글을 신고한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<ReportResponse.QuestionReportRes> reportQuestion(
			@PathVariable(name = "id") Long id,
			@RequestBody ReportRequest.QuestionReportReq request
	);
}
