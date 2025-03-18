package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.user.interfaces.request.MyPageRequest;
import kit.se.capstone2.user.interfaces.request.UserRequest;
import kit.se.capstone2.user.interfaces.response.MyPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/my-page")
public class MyPageController{

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
			@RequestBody MyPageRequest.UpdateLawyerInfo request

	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PutMapping("/lawyer")
	public CommonResponse<MyPageResponse.LawyerInfo> updateLawyerInfo(
			@RequestBody MyPageRequest.UpdateLawyerInfo request) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/questions")
	public CommonResponse<Page<MyPageResponse.QuestionInfo>> getQuestionInfo() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/lawyer/answers")
	public CommonResponse<Page<AnswerResponse.GetAnswer>> getAnswerInfo() {
		return CommonResponse.success(SuccessCode.OK, null);
	}

}
