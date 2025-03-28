package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.jwt.model.JwtToken;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;
	private SecretKey key;
	private JwtParser parser;

	private final JwtTokenProvider provider;
	private final JwtTokenExtractor extractor;
	private final JwtTokenValidator validator;

	@PostConstruct
	public void init(){
		key = Keys.hmacShaKeyFor(secret.getBytes());
		parser = Jwts.parserBuilder().setSigningKey(key).build();
		extractor.setKey(key);
		extractor.setParser(parser);
		validator.setKey(key);
		validator.setParser(parser);
		provider.setKey(key);
	}

	public JwtToken generateToken(Authentication auth){
		return provider.generateToken(auth);
	}

	public String extractToken(String authHeader){
		return extractor.extractToken(authHeader);
	}

	public Role getAuthorities(String token){
		return extractor.extractAuthorities(token);
	}
	public Boolean isExpired(String accessToken) {
		return validator.isExpired(accessToken);
	}

	public void validateRefreshToken(String refreshToken){
		try {
			validator.isExpired(refreshToken);
		}catch (ExpiredJwtException e){
			throw new BusinessLogicException(ErrorCode.REFRESH_TOKEN_EXPIRED);
		}
		String category = extractor.extractCategory(refreshToken);
		if(!JwtProperties.REFRESH_TOKEN_TYPE.equals(category)){
			throw new BusinessLogicException(ErrorCode.INVALID_TOKEN);
		}
	}

	public Boolean isAccessToken(String token) {
		String category = extractor.extractCategory(token);
		return JwtProperties.ACCESS_TOKEN_TYPE.equals(category);
	}

	public String getUsername(String token) {
		return extractor.extractUsername(token);
	}

}
