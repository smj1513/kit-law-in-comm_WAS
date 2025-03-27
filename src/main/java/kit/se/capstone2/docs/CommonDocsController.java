package kit.se.capstone2.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.code.SuccessCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@Tag(name = "공통 API", description = "공통 API")
@RestController
@RequestMapping("/common")
@Log4j2
public class CommonDocsController {
	@GetMapping("/errors")
	@Operation(summary = "에러 코드 목록 조회", description = "에러 코드 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공")
	public CommonResponse<List<ErrorExampleRes>> errors() {
		log.info("에러 코드 조회 까지 도달함 ------------------");
		return CommonResponse.success(SuccessCode.OK, Stream.of(ErrorCode.values())
				.map(ErrorExampleRes::from)
				.toList());
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ErrorExampleRes {
		private String name;
		private int code;
		private String message;

		public static ErrorExampleRes from(ErrorCode errorCode) {
			return ErrorExampleRes.builder()
					.name(errorCode.name())
					.code(errorCode.getCode())
					.message(errorCode.getMessage())
					.build();
		}
	}
}
