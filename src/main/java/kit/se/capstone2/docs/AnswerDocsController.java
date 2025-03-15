package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.answer.interfaces.request.AnswerRequest;
import kit.se.capstone2.answer.interfaces.response.AnswerResponse;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Tag(name = "답변글 관련 API", description = "답변글 관련 API 문서")
public interface AnswerDocsController {

	@Operation(summary = "답변글 목록 조회", description = "질문글에 대한 답변글 목록을 페이지네이션으로 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Page<AnswerResponse.GetAnswer>> getAnswers(
			@Schema(description = "질문글 ID", example = "1")
			Long id,
			@Schema(description = "페이지 번호", example = "0")
			int page,
			@Schema(description = "페이지 크기", example = "10")
			int size
	);

	@Operation(summary = "답변 작성", description = "변호사가 답변 글을 작성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<AnswerResponse.PostAnswer> postAnswer(
			@Schema(description = "질문글 ID", example = "1")
			Long id,
			@Schema(description = "답변글 생성 요청", implementation = AnswerRequest.AnswerPost.class)
			AnswerRequest.AnswerPost request);

	@Operation(summary = "답변 수정", description = "변호사가 자신의 답변 글을 수정한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<AnswerResponse.PutAnswer> putAnswer(
			@Schema(description = "답변글 ID", example = "1")
			Long answerId,
			@Schema(description = "답변글 수정 요청", implementation = AnswerRequest.AnswerPut.class)
			AnswerRequest.AnswerPut request);

	@Operation(summary = "답변 삭제", description = "변호사가 자신의 답변 글을 삭제한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<AnswerResponse.DeleteAnswer> deleteAnswer(
			@Schema(description = "답변글 ID", example = "1")
			Long answerId);
}
