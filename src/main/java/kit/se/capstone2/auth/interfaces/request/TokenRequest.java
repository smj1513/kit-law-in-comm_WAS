package kit.se.capstone2.auth.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TokenRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Refresh {
		private String refreshToken;
	}
}
