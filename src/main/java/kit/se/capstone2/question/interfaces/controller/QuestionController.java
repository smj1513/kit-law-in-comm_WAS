package kit.se.capstone2.question.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.question.interfaces.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

	@GetMapping
	public CommonResponse<Page<QuestionResponse.Question>> getQuestion(
			@RequestParam int page,
			@RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/{id}")
	public CommonResponse<QuestionResponse.Question> getQuestionById(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PostMapping
	public CommonResponse<QuestionResponse.Question> createQuestion(
			@RequestBody QuestionResponse.Question request
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@DeleteMapping("/{id}")
	public CommonResponse<QuestionResponse.Question> deleteQuestion(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PutMapping("/{id}")
	public CommonResponse<QuestionResponse.Question> updateQuestion(
			@PathVariable Long id,
			@RequestBody QuestionResponse.Question request
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}


}
