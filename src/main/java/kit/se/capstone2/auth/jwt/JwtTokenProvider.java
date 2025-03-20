package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.Jwts;
import kit.se.capstone2.auth.jwt.model.JwtToken;
import kit.se.capstone2.auth.jwt.model.RefreshToken;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

	@Setter
	private SecretKey key;

	public JwtToken generateToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		Map<String, Object> accessClaims = Map.of(JwtProperties.ROLE, authorities, JwtProperties.CATEGORY, JwtProperties.ACCESS_TOKEN_TYPE);
		Map<String, Object> refreshClaims = Map.of(JwtProperties.CATEGORY, JwtProperties.REFRESH_TOKEN_TYPE);
		String accessToken = generateAccessToken(authentication.getName(), accessClaims);
		RefreshToken refreshToken = generateRefreshToken(authentication.getName(), refreshClaims);

		return JwtToken.builder()
				.type("Bearer")
				.accessToken(accessToken)
				.refreshToken(refreshToken.getToken())
				.build();
	}

	private String generateAccessToken(final String username, final Map<String, Object> claims) {
		return Jwts.builder()
				.claims(claims)
				.id(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_EXPIRATION))
				.signWith(key)
				.compact();
	}

	private RefreshToken generateRefreshToken(final String username, Map<String, Object> refreshClaims) {
		Date expDate = new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_EXPIRATION);
		return RefreshToken.builder()
				.userId(username)
				.token(Jwts.builder()
						.id(username)
						.issuedAt(new Date(System.currentTimeMillis()))
						.claims(refreshClaims)
						.expiration(expDate)
						.signWith(key)
						.compact())
				.expiration(expDate.getTime())
				.build();
	}
}
