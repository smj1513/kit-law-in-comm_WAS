package kit.se.capstone2.common.exception.handler;

import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.common.exception.CustomAuthorizationException;
import kit.se.capstone2.common.exception.FileProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessLogicException.class)
	public ResponseEntity<CommonResponse<Void>> handleBusinessLogicException(BusinessLogicException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.error(e.getErrorCode()));
	}

	@ExceptionHandler(CustomAuthorizationException.class)
	public ResponseEntity<CommonResponse<Void>> handleCustomLoginException(CustomAuthorizationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.error(e.getErrorCode()));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<CommonResponse<Void>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(CommonResponse.error(ErrorCode.FILE_TYPE_UNSUPPORTED));
	}

	@ExceptionHandler(FileProcessException.class)
	public ResponseEntity<CommonResponse<Void>> handleFileProcessException(FileProcessException e) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(CommonResponse.error(e.getErrorCode()));
	}


}
