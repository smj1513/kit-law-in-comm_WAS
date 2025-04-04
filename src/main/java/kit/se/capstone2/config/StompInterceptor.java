package kit.se.capstone2.config;

import io.jsonwebtoken.ExpiredJwtException;
import kit.se.capstone2.auth.jwt.JwtProperties;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Component
@RequiredArgsConstructor
@Transactional
@Log4j2
public class StompInterceptor implements ChannelInterceptor {

	private final SecurityUtils securityUtils;
	private final JwtUtils jwtUtils;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		String authorizationHeader = String.valueOf(accessor.getNativeHeader(JwtProperties.AUTH_HEADER));

		if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
			SecurityContextHolder.clearContext();
			accessor.setUser(null);
			return message;
		}
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
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
		} else {
			Principal currentAuth = accessor.getUser();
			if (currentAuth instanceof Authentication) {
				SecurityContext context = SecurityContextHolder.getContext();
				context.setAuthentication((Authentication) currentAuth);
				SecurityContextHolder.setContext(context); // 핵심 수정 부분
			}
		}
		return message;
	}
}
