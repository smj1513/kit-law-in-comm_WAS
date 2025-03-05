package kit.se.capstone2.common.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.code.SuccessCode;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Schema(title = "공통 응답 구조")
public class CommonResponse<T> {


	@JsonProperty("isSuccess")
	@Schema(description = "성공 여부", example = "true")
	private boolean isSuccess;

	@Schema(description = "응답 코드", example = "200")
	private Integer code;

	@Schema(description = "응답 메시지", example = "성공")
	private String message;

	@Schema(description = "응답 데이터")
	private T data;

	public static <T> CommonResponse<T> success(SuccessCode successCode, T data) {
		return CommonResponse.<T>builder()
				.isSuccess(true)
				.code(successCode.getCode())
				.message(successCode.getMessage())
				.data(data)
				.build();
	}

	public static <T> CommonResponse<T> error(ErrorCode errorCode) {
		return CommonResponse.<T>builder()
				.isSuccess(false)
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build();
	}

}
