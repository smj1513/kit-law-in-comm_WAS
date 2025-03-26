package kit.se.capstone2.posts.answer.interfaces.controller;

import kit.se.capstone2.posts.answer.application.AnswerAppService;
import kit.se.capstone2.posts.answer.interfaces.request.AnswerRequest;
import kit.se.capstone2.posts.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.docs.AnswerDocsController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AnswerController implements AnswerDocsController {

	private final AnswerAppService answerAppService;

	@GetMapping("/question/{question-id}/answers")
	public CommonResponse<Page<AnswerResponse.GetAnswer>> getAnswers(@PathVariable(name = "question-id") Long questionId,
	                                                                 @RequestParam int page,
	                                                                 @RequestParam int size
	) {
		return CommonResponse.success(SuccessCode.OK, answerAppService.retrieveAnswers(questionId, page, size));
	}


	@PostMapping("/question/{question-id}/answers")
	public CommonResponse<AnswerResponse.PostAnswer> postAnswer(@PathVariable(name = "question-id") Long questionId,
	                                                            @RequestBody AnswerRequest.AnswerPost request
	) {
		return CommonResponse.success(SuccessCode.CREATED, answerAppService.createAnswer(questionId, request));
	}


	@PutMapping("/answers/{answer-id}")
	public CommonResponse<AnswerResponse.PutAnswer> putAnswer(@PathVariable(name = "answer-id") Long answerId,
	                                                          @RequestBody AnswerRequest.AnswerPut request
	) {
		return CommonResponse.success(SuccessCode.MODIFIED, answerAppService.updateAnswer(answerId, request));
	}

	@DeleteMapping("/answers/{answer-id}")
	public CommonResponse<AnswerResponse.DeleteAnswer> deleteAnswer(@PathVariable(name = "answer-id") Long answerId) {
		return CommonResponse.success(SuccessCode.DELETED,  answerAppService.removeAnswer(answerId));
	}
}
