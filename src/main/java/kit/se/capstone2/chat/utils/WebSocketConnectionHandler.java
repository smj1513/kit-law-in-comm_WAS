package kit.se.capstone2.chat.utils;

import io.jsonwebtoken.ExpiredJwtException;
import kit.se.capstone2.auth.jwt.JwtProperties;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WebSocketConnectionHandler implements CommandHandler{
	private final SecurityUtils securityUtils;
	private final JwtUtils jwtUtils;

	@Override
	public void handle(StompHeaderAccessor accessor, StompCommand command) {
		String authorizationHeader = String.valueOf(accessor.getNativeHeader(JwtProperties.AUTH_HEADER));
		String authorizationHeaderStr = authorizationHeader.replace("[", "").replace("]", "");
		String token = jwtUtils.extractToken(authorizationHeaderStr);
		try {
			jwtUtils.isExpired(token);
		} catch (ExpiredJwtException e) {
			throw new BusinessLogicException(ErrorCode.ACCESS_TOKEN_EXPIRED, "access token expired");
		}
		if (!jwtUtils.isAccessToken(token)) {
			throw new BusinessLogicException(ErrorCode.INVALID_TOKEN, "invalid token");
		}
		Authentication authentication = securityUtils.setAuthentication(token);
		accessor.setUser(authentication);
	}

	@Override
	public boolean supports(StompCommand command) {
		return StompCommand.CONNECT.equals(command);
	}
}
