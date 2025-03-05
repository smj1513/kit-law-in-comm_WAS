package kit.se.capstone2.common.exception;

import kit.se.capstone2.common.api.code.ErrorCode;
import lombok.Getter;

@Getter
public abstract class AbstractErrorException extends RuntimeException{
	protected final ErrorCode errorCode;

	public AbstractErrorException(ErrorCode errorCode){
		this.errorCode = errorCode;
	}

	public AbstractErrorException(ErrorCode errorCode, String message){
		this.errorCode = errorCode;
		this.errorCode.setMessage(message);
	}
}
