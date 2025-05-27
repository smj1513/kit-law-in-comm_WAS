package kit.se.capstone2.auth.jwt;

import io.jsonwebtoken.Jwts;
import kit.se.capstone2.auth.domain.model.Account;
import kit.se.capstone2.auth.interfaces.response.LoginResponse;
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

@Setter
public class JwtTokenProvider {

	private SecretKey key;

	public LoginResponse generateToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		Account account = (Account) authentication.getPrincipal();
		Long id = account.getId();
		Map<String, Object> accessClaims = Map.of(JwtProperties.ROLE, authorities, JwtProperties.CATEGORY, JwtProperties.ACCESS_TOKEN_TYPE, JwtProperties.ID, id);
		Map<String, Object> refreshClaims = Map.of(JwtProperties.ROLE, authorities, JwtProperties.CATEGORY, JwtProperties.REFRESH_TOKEN_TYPE, JwtProperties.ID, id);
		String accessToken = generateAccessToken(authentication.getName(), accessClaims);
		RefreshToken refreshToken = generateRefreshToken(authentication.getName(), refreshClaims);

		return LoginResponse.builder()
				.header(JwtProperties.AUTH_HEADER)
				.type(JwtProperties.TOKEN_PREFIX)
				.accessToken(accessToken)
				.refreshToken(refreshToken.getToken())
				.build();
	}

	private String generateAccessToken(final String username, final Map<String, Object> claims) {
		return Jwts.builder()
				.id(username)
				.claims(claims)
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
						.compact()
				)
				.expiration(expDate.getTime())
				.build();
	}
}
