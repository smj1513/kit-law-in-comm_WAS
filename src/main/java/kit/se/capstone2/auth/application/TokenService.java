package kit.se.capstone2.auth.application;

import io.jsonwebtoken.lang.Collections;
import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.interfaces.request.TokenRequest;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.jwt.model.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtUtils jwtUtils;

	public JwtToken refresh(TokenRequest.Refresh request) {
		String refreshToken = request.getRefreshToken();
		jwtUtils.validateRefreshToken(refreshToken);
		String username = jwtUtils.getUsername(refreshToken);
		Role authorities = jwtUtils.getAuthorities(refreshToken);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, UUID.randomUUID().toString(), Collections.of(authorities));
		return jwtUtils.generateToken(usernamePasswordAuthenticationToken);
	}
}
