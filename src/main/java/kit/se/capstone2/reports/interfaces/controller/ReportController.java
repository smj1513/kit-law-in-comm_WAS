package kit.se.capstone2.reports.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.ReportDocsController;
import kit.se.capstone2.reports.application.ReportAppService;
import kit.se.capstone2.reports.interfaces.request.ReportRequest;
import kit.se.capstone2.reports.interfaces.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController implements ReportDocsController {
	private final ReportAppService reportAppService;

	@GetMapping("/answer/details/{answer-id}")
	public CommonResponse<Page<ReportResponse.AnswerReportDetails>> getAnswerReportDetails(
			@PathVariable(name = "answer-id") Long answerId,
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, reportAppService.getAnswerReportDetails(answerId, page, size));
	}

	@GetMapping("/question/details/{question-id}")
	public CommonResponse<Page<ReportResponse.QuestionReportDetails>> getQuestionReportDetails(
			@PathVariable(name = "question-id") Long questionId,
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, reportAppService.getQuestionReportDetails(questionId, page, size));
	}


	@PostMapping("/answers/{id}")
	public CommonResponse<ReportResponse.AnswerReportRes> reportAnswer(
			@PathVariable(name = "id") Long id,
			@RequestBody
			@Validated ReportRequest.AnswerReportReq request
	) {
		return CommonResponse.success(SuccessCode.CREATED, reportAppService.reportAnswer(id, request));
	}

	@PostMapping("/questions/{id}")
	public CommonResponse<ReportResponse.QuestionReportRes> reportQuestion(
			@PathVariable(name = "id") Long id,
			@RequestBody
			@Validated ReportRequest.QuestionReportReq request
	) {
		return CommonResponse.success(SuccessCode.CREATED,
				reportAppService.reportQuestion(id, request)
		);
	}

}
