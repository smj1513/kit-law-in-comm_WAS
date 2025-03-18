package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.domain.enums.ApprovalStatus;
import kit.se.capstone2.user.interfaces.response.AdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/admin")
public class AdminController {

	@GetMapping("/reports/answers")
	public CommonResponse<Page<AdminResponse.ReportedAnswer>> getReportedPosts() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/reports/questions")
	public CommonResponse<Page<AdminResponse.ReportedQuestion>> getReportedQuestions() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/confirmations/lawyers")
	public CommonResponse<Page<AdminResponse.ConfirmationLawyer>> getConfirmationLawyers() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PostMapping("/confirmations/lawyers/{lawyerId}")
	public CommonResponse<AdminResponse.ConfirmationLawyer> confirmLawyer(
			@PathVariable Long lawyerId,
			@RequestParam ApprovalStatus status
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

}
