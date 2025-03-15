package kit.se.capstone2.answer.interfaces.controller;

import kit.se.capstone2.answer.interfaces.request.AnswerRequest;
import kit.se.capstone2.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.AnswerDocsController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AnswerController implements AnswerDocsController {

	@GetMapping("/question/{question-id}/answers")
	public CommonResponse<Page<AnswerResponse.GetAnswer>> getAnswers(@PathVariable(name = "question-id") Long id,
	                                                                 @RequestParam int page,
	                                                                 @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}


	@PostMapping("/question/{question-id}/answers")
	public CommonResponse<AnswerResponse.PostAnswer> postAnswer(@PathVariable(name = "question-id") Long id,
	                                                            @RequestBody AnswerRequest.AnswerPost request
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}


	@PutMapping("/answers/{answer-id}")
	public CommonResponse<AnswerResponse.PutAnswer> putAnswer(@PathVariable(name = "answer-id") Long answerId,
	                                                          @RequestBody AnswerRequest.AnswerPut request
	) {
		return CommonResponse.success(SuccessCode.OK, null);
	}


	@DeleteMapping("/answers/{answer-id}")
	public CommonResponse<AnswerResponse.DeleteAnswer> deleteAnswer(@PathVariable(name = "answer-id") Long answerId) {
		return CommonResponse.success(SuccessCode.OK, null);
	}
}
