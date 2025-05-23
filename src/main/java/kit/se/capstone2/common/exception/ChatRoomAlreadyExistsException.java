package kit.se.capstone2.common.exception;

import kit.se.capstone2.common.api.code.ErrorCode;

public class ChatRoomAlreadyExistsException extends AbstractErrorException{
	public ChatRoomAlreadyExistsException(ErrorCode errorCode) {
		super(errorCode);
	}

	public ChatRoomAlreadyExistsException(ErrorCode errorCode, String message){
		super(errorCode, message);
	}

	public ChatRoomAlreadyExistsException(ErrorCode errorCode, String message, Object body){
		super(errorCode, message, body);
	}
}
