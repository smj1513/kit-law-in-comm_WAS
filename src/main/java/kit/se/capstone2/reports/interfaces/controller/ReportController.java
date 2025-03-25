package kit.se.capstone2.reports.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.ReportDocsController;
import kit.se.capstone2.reports.application.ReportAppService;
import kit.se.capstone2.reports.interfaces.request.ReportRequest;
import kit.se.capstone2.reports.interfaces.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController implements ReportDocsController {
	private final ReportAppService reportAppService;

	@PostMapping("/answers/{id}")
	public CommonResponse<ReportResponse.AnswerReportRes> reportAnswer(
			@PathVariable(name = "id") Long id,
			@RequestBody ReportRequest.AnswerReportReq request
	) {
		return CommonResponse.success(SuccessCode.CREATED, null);
	}

	@PostMapping("/questions/{id}")
	public CommonResponse<ReportResponse.QuestionReportRes> reportQuestion(
			@PathVariable(name = "id") Long id,
			@RequestBody ReportRequest.QuestionReportReq request
	) {
		return CommonResponse.success(SuccessCode.CREATED,
				reportAppService.reportQuestion(id, request)
		);
	}

}
