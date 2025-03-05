package kit.se.capstone2.docs;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.question.interfaces.response.QuestionResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "QuestionDocsController", description = "질문글 관련 API 문서")
public interface QuestionDocsController {
	@Operation(summary = "질문글 목록 조회", description = "질문 글에 대한 목록을 페이지네이션으로 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<QuestionResponse.Question>> getQuestion(
			@Schema(description = "페이지 번호", example = "0")
			int page,
			@Schema(description = "페이지 크기", example = "10")
			int size
	);

	@Operation(summary = "질문글 상세 조회", description = "특정 ID에 대한 질문의 내용을 상세하게 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<QuestionResponse.Question> getQuestionById(
			@Schema(description = "질문글 ID", example = "1")
			Long id
	);

	@Operation(summary = "질문 작성", description = "일반 사용자가 질문 글을 작성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<QuestionResponse.Question> createQuestion(
			@Schema(description = "질문글 생성 요청", implementation = QuestionResponse.Question.class)
			QuestionResponse.Question request
	);

	@Operation(summary = "질문글 삭제", description = "특정 ID의 질문글을 삭제한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<QuestionResponse.Question> deleteQuestion(
			@Schema(description = "질문글 ID", example = "1")
			Long id
	);

	@Operation(summary = "질문글 수정", description = "특정 ID의 질문글을 수정한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<QuestionResponse.Question> updateQuestion(
			@Schema(description = "질문글 ID", example = "1")
			Long id,
			@Schema(description = "질문글 수정 요청", implementation = QuestionResponse.Question.class)
			QuestionResponse.Question request
	);
}
