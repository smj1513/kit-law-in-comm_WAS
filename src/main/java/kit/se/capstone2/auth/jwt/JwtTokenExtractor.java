package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import kit.se.capstone2.auth.domain.enums.Role;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

@Component
public class JwtTokenExtractor {

	@Setter
	private SecretKey key;

	private JwtParser parser;

	@PostConstruct
	public void init(){
		parser = Jwts.parser().verifyWith(key).build();
	}

	public String extractUsername(String token) {
		Jws<Claims> claimsJws = parser.parseSignedClaims(token);
		Claims payload = claimsJws.getPayload();
		return payload.getId();
	}

	public Role extractAuthorities(String token) {
		Jws<Claims> claimsJws = parser.parseSignedClaims(token);
		Claims payload = claimsJws.getPayload();
		Role role = Role.valueOf(payload.get(JwtProperties.ROLE, String.class));
		return role;
	}

	public String extractCategory(String token) {
		Jws<Claims> claimsJws = parser.parseSignedClaims(token);
		Claims payload = claimsJws.getPayload();
		return payload.get(JwtProperties.CATEGORY, String.class);
	}

	public String extractToken(String authHeader) {
		if (StringUtils.hasText(authHeader) && authHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			return authHeader.substring(JwtProperties.TOKEN_PREFIX.length());
		}
		return authHeader;
	}

}
