package kit.se.capstone2.auth.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	private String username;
	private String password;
}
