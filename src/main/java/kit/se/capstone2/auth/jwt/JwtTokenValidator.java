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
@Setter
public class JwtTokenValidator {

	private SecretKey key;
	private JwtParser parser;

	public Boolean isExpired(String token) {
		return parser
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration()
				.before(Date.from(LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).toInstant()));
	}

}
