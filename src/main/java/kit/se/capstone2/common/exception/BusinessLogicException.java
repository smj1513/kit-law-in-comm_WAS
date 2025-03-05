package kit.se.capstone2.common.exception;


import kit.se.capstone2.common.api.code.ErrorCode;

public class BusinessLogicException extends AbstractErrorException {

	public BusinessLogicException(ErrorCode errorCode) {
		super(errorCode);
	}

	public BusinessLogicException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
