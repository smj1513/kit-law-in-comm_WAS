package kit.se.capstone2.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {


	//위에 추가
	ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, EntityCode.TOKEN, ExceptionCode.UNAUTHORIZED, "Access Token Expired"),
	FILE_PROCESS_FAILURE(HttpStatus.SERVICE_UNAVAILABLE, EntityCode.FILE, ExceptionCode.BAD_REQUEST, "File Process Failure"),
	FILE_TYPE_UNSUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE, EntityCode.FILE, ExceptionCode.BAD_REQUEST, "File Type Unsupported"),
	INFERENCE_FAILURE(HttpStatus.FAILED_DEPENDENCY, EntityCode.COMMON, ExceptionCode.CONFLICT, "Inference Failure"),
	NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, EntityCode.COMMON, ExceptionCode.NOT_FOUND, "Entity Not Found"),
	INTENAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, EntityCode.COMMON, ExceptionCode.INTERNAL_SERVER_ERROR, "Internal Server Error"),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, EntityCode.USER, ExceptionCode.NOT_FOUND, "User Not Found"),
	INVALID_TOKEN(HttpStatus.BAD_REQUEST, EntityCode.TOKEN , ExceptionCode.BAD_REQUEST , "Invalid Token"),
	LOGIN_FAILED(HttpStatus.UNAUTHORIZED, EntityCode.USER, ExceptionCode.UNAUTHORIZED, "Login Failed"),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, EntityCode.TOKEN ,ExceptionCode.EXPIRATION , "RefreshToken Expired" ),
	NO_PERMISSION(HttpStatus.FORBIDDEN, EntityCode.USER, ExceptionCode.BAD_REQUEST ,"You don't have Authority" ),
	INVAILD_REQUEST(HttpStatus.BAD_REQUEST, EntityCode.TOKEN , ExceptionCode.UNAUTHORIZED, "Invalid Request"),
	ALREADY_REPORTED(HttpStatus.TOO_MANY_REQUESTS, EntityCode.REPORT , ExceptionCode.CONFLICT, "Already Reported"),
	CHAT_ROOM_CREATED_FAILED(HttpStatus.CONFLICT, EntityCode.CHAT , ExceptionCode.COMMON, "ChatRoom Created Failed" ),
	ENTITY_DUPLICATED(HttpStatus.CONFLICT, EntityCode.COMMON, ExceptionCode.CONFLICT, "Entity Duplicated"),
	CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, EntityCode.COMMON , ExceptionCode.VIOLATION ,"Constraints Violation" ),
	ALREADY_CHATROOM_CREATED(HttpStatus.CONFLICT, EntityCode.CHAT, ExceptionCode.CONFLICT, "채팅방이 이미 존재합니다."),
	EXCEEDED_CHATROOM_CREATION(HttpStatus.TOO_MANY_REQUESTS, EntityCode.CHAT, ExceptionCode.TOO_MANY_REQUESTS, "채팅방 개수를 초과하였습니다")
	;
	private final HttpStatus httpStatus;
	private final EntityCode entityCode;
	private final ExceptionCode exceptionCode;

	@Setter
	private String message;

	public Integer getCode(){
		return httpStatus.value() * 10000 + entityCode.getValue() * 100 + exceptionCode.getValue();
	}
}
