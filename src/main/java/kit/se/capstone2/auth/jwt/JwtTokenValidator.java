package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.*;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Component
@Log4j2
public class JwtTokenValidator {
	@Setter
	private SecretKey key;

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
	public Boolean isExpired(String accessToken) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(accessToken)
				.getPayload()
				.getExpiration()
				.before(Date.from(LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).toInstant()));
	}
}
