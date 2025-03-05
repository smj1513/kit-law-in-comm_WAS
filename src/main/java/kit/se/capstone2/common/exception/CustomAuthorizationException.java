package kit.se.capstone2.common.exception;


import kit.se.capstone2.common.api.code.ErrorCode;

public class CustomAuthorizationException extends AbstractErrorException {
	public CustomAuthorizationException(ErrorCode errorCode) {
		super(errorCode);
	}
	public CustomAuthorizationException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}

}
