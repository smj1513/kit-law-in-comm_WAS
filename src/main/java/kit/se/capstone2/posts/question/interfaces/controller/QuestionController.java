package kit.se.capstone2.posts.question.interfaces.controller;

import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.QuestionDocsController;
import kit.se.capstone2.posts.question.application.QuestionAppService;
import kit.se.capstone2.posts.question.interfaces.request.QuestionRequest;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController implements QuestionDocsController {

	private final QuestionAppService questionAppService;

	@GetMapping("/legal-speciality")
	public CommonResponse<Page<QuestionResponse.PostQuestion>> getQuestionByLegalSpeciality(@RequestParam LegalSpeciality legalSpeciality, @RequestParam int page, @RequestParam int size) {
		return CommonResponse.success(SuccessCode.OK, questionAppService.getQuestionByLegalSpeciality(legalSpeciality, page, size));
	}

	@GetMapping
	public CommonResponse<Page<QuestionResponse.PostQuestion>> getQuestions(
			@RequestParam int page,
			@RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, questionAppService.getQuestions(page, size));
	}

	@GetMapping("/{id}")
	public CommonResponse<QuestionResponse.PostQuestion> getQuestionById(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.OK, questionAppService.retrievalQuestionDetails(id));
	}

	@PostMapping
	public CommonResponse<QuestionResponse.PostQuestion> createQuestion(
			@RequestBody
			@Validated QuestionRequest.Create request
	) {
		return CommonResponse.success(SuccessCode.CREATED, questionAppService.createQuestion(request));
	}

	@DeleteMapping("/{id}")
	public CommonResponse<QuestionResponse.PostQuestion> deleteQuestion(
			@PathVariable Long id
	) {
		return CommonResponse.success(SuccessCode.DELETED, questionAppService.deleteQuestion(id));
	}

	@PutMapping("/{id}")
	public CommonResponse<QuestionResponse.PostQuestion> updateQuestion(
			@PathVariable Long id,
			@RequestBody QuestionRequest.Create request
	) {
		return CommonResponse.success(SuccessCode.MODIFIED, questionAppService.updateQuestion(id, request));
	}

	@GetMapping("/search")
	public CommonResponse<Page<QuestionResponse.PostQuestion>> searchQuestions(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) LegalSpeciality legalSpeciality,
			@RequestParam int page,
			@RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, questionAppService.searchQuestions(keyword, legalSpeciality,page, size));
	}


}
