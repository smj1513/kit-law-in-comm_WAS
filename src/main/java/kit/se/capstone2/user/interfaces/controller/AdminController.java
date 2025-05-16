package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.AdminDocsController;
import kit.se.capstone2.user.application.AdminAppService;
import kit.se.capstone2.user.interfaces.request.AdminRequest;
import kit.se.capstone2.user.interfaces.response.AdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/admin")
public class AdminController implements AdminDocsController {

	private final AdminAppService appService;

	@GetMapping("/reports/answers")
	public CommonResponse<Page<AdminResponse.ReportedAnswer>> getReportedAnswer(@RequestParam int threshold, @RequestParam int page, @RequestParam int size) {
		return CommonResponse.success(SuccessCode.OK, appService.retrieveReportedAnswers(threshold, page, size));
	}

	@GetMapping("/reports/questions")
	public CommonResponse<Page<AdminResponse.ReportedQuestion>> getReportedQuestions(
			@RequestParam int threshold, @RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, appService.retrieveReportedQuestion(threshold, page, size));
	}

	@GetMapping("/confirmations/lawyers")
	public CommonResponse<Page<AdminResponse.ConfirmationLawyer>> getConfirmationLawyers(
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, appService.getConfirmationLawyers(page, size));
	}

	@GetMapping("/confirmations/lawyers/{id}")
	public CommonResponse<AdminResponse.ConfirmationLawyerDetails> getConfirmationLawyerDetails(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, appService.getConfirmationLawyerDetails(id));
	}

	@PostMapping("/confirmations/lawyers/{id}")
	public CommonResponse<AdminResponse.ConfirmationLawyer> confirmLawyer(
			@PathVariable Long id,
			@RequestBody AdminRequest.Confirmation request
	) {
		return CommonResponse.success(SuccessCode.OK, appService.confirmLawyer(id, request));
	}

	@DeleteMapping("/questions")
	public CommonResponse<AdminResponse.RemoveQuestions> removeQuestions(
			@RequestBody AdminRequest.RemoveQuestionsReq request
	) {
		return CommonResponse.success(SuccessCode.OK, appService.removeQuestions(request));
	}

	@DeleteMapping("/answers")
	public CommonResponse<AdminResponse.RemoveAnswers> removeAnswers(
			@RequestBody AdminRequest.RemoveAnswersReq request
	) {
		return CommonResponse.success(SuccessCode.OK, appService.removeAnswers(request));
	}

}
