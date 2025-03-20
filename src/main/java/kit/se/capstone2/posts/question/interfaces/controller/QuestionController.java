package kit.se.capstone2.posts.question.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.QuestionDocsController;
import kit.se.capstone2.posts.question.interfaces.request.QuestionRequest;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController implements QuestionDocsController {

	@GetMapping("/legal-speciality")
	public CommonResponse<Page<QuestionResponse.PostQuestion>> getQuestionByLegalSpeciality(@RequestParam LegalSpeciality legalSpeciality, @RequestParam int page, @RequestParam int size) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping
	public CommonResponse<Page<QuestionResponse.PostQuestion>> getQuestions(
			@RequestParam int page,
			@RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@GetMapping("/{id}")
	public CommonResponse<QuestionResponse.PostQuestion> getQuestionById(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}

	@PostMapping
	public CommonResponse<QuestionResponse.PostQuestion> createQuestion(
			@RequestBody QuestionRequest.Create request
	) {
		return CommonResponse.success(SuccessCode.CREATED, null);
	}

	@DeleteMapping("/{id}")
	public CommonResponse<QuestionResponse.PostQuestion> deleteQuestion(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.DELETED, null);
	}

	@PutMapping("/{id}")
	public CommonResponse<QuestionResponse.PostQuestion> updateQuestion(
			@PathVariable Long id,
			@RequestBody QuestionRequest.Create request
	) {
		return CommonResponse.success(SuccessCode.MODIFIED, null);
	}


}
