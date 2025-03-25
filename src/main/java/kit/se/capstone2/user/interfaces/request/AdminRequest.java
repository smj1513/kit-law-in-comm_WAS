package kit.se.capstone2.user.interfaces.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminRequest {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Confirmation{
		@NotNull
		private Boolean isApprove;
	}
}
