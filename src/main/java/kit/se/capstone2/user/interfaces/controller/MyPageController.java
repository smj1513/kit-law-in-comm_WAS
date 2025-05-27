package kit.se.capstone2.user.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.MyPageDocsController;
import kit.se.capstone2.user.application.MyPageAppService;
import kit.se.capstone2.user.interfaces.request.MyPageRequest;
import kit.se.capstone2.user.interfaces.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/my-page")
@RequiredArgsConstructor
public class MyPageController implements MyPageDocsController {
	private final MyPageAppService myPageAppService;

	@GetMapping
	public CommonResponse<MyPageResponse.GeneralInfo> getGeneralInfo() {
		return CommonResponse.success(SuccessCode.OK, myPageAppService.getGeneralInfo());
	}

	@GetMapping("/lawyer/{id}")
	public CommonResponse<MyPageResponse.LawyerInfo> getLawyerInfo(@PathVariable Long id) {
		return CommonResponse.success(SuccessCode.OK,myPageAppService.getLawyerInfo(id));
	}

	@PutMapping
	public CommonResponse<MyPageResponse.GeneralInfo> updateGeneralInfo(
			@RequestBody MyPageRequest.UpdateGeneralInfo request
	) {
		return CommonResponse.success(SuccessCode.OK, myPageAppService.updateGeneralInfo(request));
	}

	//TODO 이 부분 어떻게 하면 좋을지 고민,,,
	@PutMapping("/lawyer")
	public CommonResponse<MyPageResponse.LawyerInfo> updateLawyerInfo(
			@RequestBody MyPageRequest.UpdateLawyerInfo request) {
		return CommonResponse.success(SuccessCode.OK, myPageAppService.updateLawyerInfo(request));
	}

	@GetMapping("/questions")
	public CommonResponse<Page<MyPageResponse.QuestionInfo>> getQuestionInfo(
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, myPageAppService.retrieveQuestions(page, size));
	}

	@GetMapping("/lawyer/answers")
	public CommonResponse<Page<MyPageResponse.AnswerInfo>> getAnswerInfo(
			@RequestParam int page, @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, myPageAppService.retrieveAnswers(page, size));
	}

}
