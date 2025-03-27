package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.posts.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.MyPageDocsController;
import kit.se.capstone2.user.interfaces.request.MyPageRequest;
import kit.se.capstone2.user.interfaces.response.MyPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/my-page")
public class MyPageController implements MyPageDocsController {

	@GetMapping
	public CommonResponse<MyPageResponse.GeneralInfo> getGeneralInfo() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/lawyer")
	public CommonResponse<MyPageResponse.LawyerInfo> getLawyerInfo() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PutMapping
	public CommonResponse<MyPageResponse.GeneralInfo> updateGeneralInfo(
			@RequestBody MyPageRequest.UpdateGeneralInfo request

	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PutMapping("/lawyer")
	public CommonResponse<MyPageResponse.LawyerInfo> updateLawyerInfo(
			@RequestBody MyPageRequest.UpdateLawyerInfo request) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/questions")
	public CommonResponse<Page<MyPageResponse.QuestionInfo>> getQuestionInfo(
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/lawyer/answers")
	public CommonResponse<Page<AnswerResponse.GetAnswer>> getAnswerInfo(
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

}
