package kit.se.capstone2.docs;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.posts.question.interfaces.request.QuestionRequest;
import kit.se.capstone2.posts.question.interfaces.response.QuestionResponse;
import kit.se.capstone2.user.domain.enums.LegalSpeciality;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "상담글 관련 API", description = "상담글 관련 API 문서")
public interface QuestionDocsController {

	@Operation(summary = "특정 법률 분야의 상담글 목록 조회", description = "특정 법률 분야의 상담글을 페이지네이션으로 조회한다.", deprecated = true)
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<Page<QuestionResponse.PostQuestion>> getQuestionByLegalSpeciality(@RequestParam LegalSpeciality legalSpeciality,
	                                                                                 @RequestParam int page,
	                                                                                 @RequestParam int size);

	@Operation(summary = "상담글 목록 조회", description = "상담글에 대한 목록을 페이지네이션으로 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<Page<QuestionResponse.PostQuestion>> getQuestions(
			@Schema(description = "페이지 번호", example = "0")
			int page,
			@Schema(description = "페이지 크기", example = "10")
			int size
	);

	@Operation(summary = "상담글 상세 조회", description = "상담글 ID에 대한 질문의 내용을 상세하게 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<QuestionResponse.PostQuestion> getQuestionById(
			@Schema(description = "질문글 ID", example = "1")
			Long id
	);

	@Operation(summary = "상담글 작성", description = "상담글 사용자가 상담 글을 작성한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<QuestionResponse.PostQuestion> createQuestion(
			@Schema(description = "질문글 생성 요청", implementation = QuestionRequest.Create.class)
			QuestionRequest.Create request
	);

	@Operation(summary = "상담글 삭제", description = "특정 ID의 상담글을 삭제한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<QuestionResponse.PostQuestion> deleteQuestion(
			@Schema(description = "질문글 ID", example = "1")
			Long id
	);

	@Operation(summary = "상담글 수정", description = "특정 ID의 상담글을 수정한다.")
	@ApiResponse(responseCode = "200", description = "성공")
	CommonResponse<QuestionResponse.PostQuestion> updateQuestion(
			@Schema(description = "상담글 ID", example = "1")
			Long id,
			@Schema(description = "상담글 수정 요청", implementation = QuestionRequest.Create.class)
			QuestionRequest.Create request
	);
}
