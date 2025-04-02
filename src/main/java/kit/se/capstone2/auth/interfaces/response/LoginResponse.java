package kit.se.capstone2.auth.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String header;
	private String type;
	private String accessToken;
	private String refreshToken;
}
