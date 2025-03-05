package kit.se.capstone2.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SuccessCode {

	OK(HttpStatus.OK, "OK"),
	CREATED(HttpStatus.CREATED, "Created"),
	DELETED(HttpStatus.OK, "Deleted"),
	MODIFIED(HttpStatus.OK, "Modified");


	private HttpStatus status;

	@Getter
	private String message;

	public Integer getCode() {
		return status.value();
	}

}
