package kit.se.capstone2.common.api.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EntityCode {
	COMMON(0),
	POST(1),
	PROFILE(2),
	USER(3),
	FILE(4),
	QUESTION(5),
	TOKEN(6);

	private final Integer value;
}
