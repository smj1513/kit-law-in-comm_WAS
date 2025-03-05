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
	@Operation(summary = "AI 채팅 세션 생성", description = "채팅 세션을 생성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<QuestionResponse.Question>> getQuestion(
			@Schema(description = "페이지 번호", example = "0")
			int page,
			@Schema(description = "페이지 크기", example = "10")
			int size
	);

	@Operation(summary = "AI 채팅 세션 생성", description = "채팅 세션을 생성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<QuestionResponse.Question> getQuestionById(
			@Schema(description = "질문글 ID", example = "1")
			Long id
	);

	@Operation(summary = "AI 채팅 세션 생성", description = "채팅 세션을 생성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<QuestionResponse.Question> createQuestion(
			@Schema(description = "질문글 생성 요청", implementation = QuestionResponse.Question.class)
			QuestionResponse.Question request
	);

	@Operation(summary = "AI 채팅 세션 생성", description = "채팅 세션을 생성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<QuestionResponse.Question> deleteQuestion(
			@Schema(description = "질문글 ID", example = "1")
			Long id
	);

	@Operation(summary = "AI 채팅 세션 생성", description = "채팅 세션을 생성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<QuestionResponse.Question> updateQuestion(
			@Schema(description = "질문글 ID", example = "1")
			Long id,
			@Schema(description = "질문글 수정 요청", implementation = QuestionResponse.Question.class)
			QuestionResponse.Question request
	);
}
