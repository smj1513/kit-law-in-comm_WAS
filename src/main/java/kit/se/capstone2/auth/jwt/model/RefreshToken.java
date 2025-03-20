package kit.se.capstone2.auth.jwt.model;

import jakarta.persistence.Id;
import kit.se.capstone2.auth.jwt.JwtProperties;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@RedisHash(value = "refresh_token")
public class RefreshToken {

	@Id
	private String userId;
	private String token;

	@TimeToLive
	private Long expiration;
}
