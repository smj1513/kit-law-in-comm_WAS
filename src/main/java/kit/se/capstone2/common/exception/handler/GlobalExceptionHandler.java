package kit.se.capstone2.common.exception.handler;

import jakarta.validation.ConstraintViolationException;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.common.exception.CustomAuthorizationException;
import kit.se.capstone2.common.exception.FileProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<Void>> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.error(ErrorCode.INTENAL_SERVER_ERROR, e.getCause().getMessage()));
	}

	@ExceptionHandler(BusinessLogicException.class)
	public ResponseEntity<CommonResponse<Void>> handleBusinessLogicException(BusinessLogicException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(CommonResponse.error(e.getErrorCode()));
	}

	@ExceptionHandler(CustomAuthorizationException.class)
	public ResponseEntity<CommonResponse<Void>> handleCustomLoginException(CustomAuthorizationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.error(e.getErrorCode(), e.getMessage()));
	}


	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<CommonResponse<Void>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(CommonResponse.error(ErrorCode.FILE_TYPE_UNSUPPORTED));
	}

	@ExceptionHandler(FileProcessException.class)
	public ResponseEntity<CommonResponse<Void>> handleFileProcessException(FileProcessException e) {
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(CommonResponse.error(e.getErrorCode()));
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<CommonResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.error(ErrorCode.CONSTRAINT_VIOLATION, e.getCause().getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CommonResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.error(ErrorCode.CONSTRAINT_VIOLATION, e.getMessage()));
	}
}
