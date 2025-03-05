package kit.se.capstone2.common.exception;

import kit.se.capstone2.common.api.code.ErrorCode;

public class FileProcessException extends AbstractErrorException {
	public FileProcessException(ErrorCode errorCode) {
		super(errorCode);
	}
	public FileProcessException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
