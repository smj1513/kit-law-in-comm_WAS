package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@Setter
public class JwtTokenValidator {

	private SecretKey key;

	private JwtParser parser;

	public Boolean isExpired(String accessToken) {
		try {
			parser.parseSignedClaims(accessToken);
			return false;
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	public Long getRemainingMillis(String token) {
		Jws<Claims> claimsJws = parser.parseSignedClaims(token);
		Claims payload = claimsJws.getPayload();
		return payload.getExpiration().getTime() - new Date().getTime();
	}


}