package kit.se.capstone2.question.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.QuestionDocsController;
import kit.se.capstone2.question.interfaces.request.QuestionRequest;
import kit.se.capstone2.question.interfaces.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController implements QuestionDocsController {

	@GetMapping
	public CommonResponse<Page<QuestionResponse.Post>> getQuestions(
			@RequestParam int page,
			@RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/{id}")
	public CommonResponse<QuestionResponse.Post> getQuestionById(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PostMapping
	public CommonResponse<QuestionResponse.Post> createQuestion(
			@RequestBody QuestionRequest.Post request
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@DeleteMapping("/{id}")
	public CommonResponse<QuestionResponse.Post> deleteQuestion(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PutMapping("/{id}")
	public CommonResponse<QuestionResponse.Post> updateQuestion(
			@PathVariable Long id,
			@RequestBody QuestionRequest.Post request
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}


}
