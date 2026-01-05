package kit.se.capstone2.auth.application;

import kit.se.capstone2.auth.domain.enums.Role;
import kit.se.capstone2.auth.interfaces.request.TokenRequest;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.interfaces.response.LoginResponse;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import kit.se.capstone2.auth.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtUtils jwtUtils;
	private final RedisService redisService;

	public LoginResponse refresh(TokenRequest.Refresh request) {
		String refreshToken = request.getRefreshToken();

		if (redisService.isBlackListed(refreshToken)) {
			throw new BusinessLogicException(ErrorCode.INVALID_TOKEN, "로그아웃된 토큰입니다.");
		}

		jwtUtils.validateRefreshToken(refreshToken);
		String username = jwtUtils.getUsername(refreshToken);
		Role authorities = jwtUtils.getAuthorities(refreshToken);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, UUID.randomUUID().toString(), List.of(authorities));
		return jwtUtils.generateToken(usernamePasswordAuthenticationToken);
	}

	public void logout(TokenRequest.Logout request){
		String refreshToken = request.getRefreshToken();
		Long remainingMillis = jwtUtils.getRemainingMillis(refreshToken);
		redisService.setBlackList(refreshToken, remainingMillis);
	}


}
