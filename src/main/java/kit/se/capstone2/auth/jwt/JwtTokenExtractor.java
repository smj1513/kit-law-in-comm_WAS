package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;

@Slf4j
@Component
@Setter
public class JwtTokenExtractor {

	private SecretKey key;

	private JwtParser parser;


	public String extractUsername(String token) {
		Jws<Claims> claimsJws = parser.parseClaimsJws(token);
		Claims payload = claimsJws.getBody();
		return payload.getId();
	}

	public Role extractAuthorities(String token) {
		Jws<Claims> claimsJws = parser.parseClaimsJws(token);
		Claims payload = claimsJws.getBody();
		Role role = Role.valueOf(payload.get(JwtProperties.ROLE, String.class));
		return role;
	}

	public String extractCategory(String token) {
		Jws<Claims> claimsJws = parser.parseClaimsJws(token);
		Claims payload = claimsJws.getBody();
		return payload.get(JwtProperties.CATEGORY, String.class);
	}

	public String extractToken(String authHeader) {
		//StringUtils.hasText(authHeader) &&
		log.info("authHeader: {}", authHeader);
		if (StringUtils.hasText(authHeader) && authHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			log.info("if 로 들어옴 extractToken: {}", authHeader);
			return authHeader.substring(JwtProperties.TOKEN_PREFIX.length());
		}
		else{
			throw new BusinessLogicException(ErrorCode.INVALID_TOKEN, "토큰 검증에 실패하였습니다.");
		}
	}

}
