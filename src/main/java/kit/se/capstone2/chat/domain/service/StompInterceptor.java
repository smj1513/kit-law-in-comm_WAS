package kit.se.capstone2.chat.domain.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletOutputStream;
import kit.se.capstone2.auth.jwt.JwtProperties;
import kit.se.capstone2.auth.jwt.JwtUtils;
import kit.se.capstone2.auth.security.SecurityUtils;
import kit.se.capstone2.common.api.code.ErrorCode;
import kit.se.capstone2.common.api.response.CommonResponse;
import kit.se.capstone2.common.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
		StompCommand command = accessor.getCommand();

		if (command.equals(StompCommand.UNSUBSCRIBE) ||
				command.equals(StompCommand.MESSAGE) ||
				command.equals(StompCommand.CONNECTED) ||
				command.equals(StompCommand.SEND))
		{
			return message;
		} else if (command.equals(StompCommand.ERROR)) {
			throw new MessageDeliveryException("error");
		}
//
//		if (authorizationHeader == null) {
//			log.info("chat header가 없는 요청입니다.");
//			throw new MalformedJwtException("jwt");
//		}
//		String token = jwtUtils.extractToken(authorizationHeader);
//		try {
//			jwtUtils.isExpired(token);
//		} catch (ExpiredJwtException e) {
//			throw new BusinessLogicException(ErrorCode.ACCESS_TOKEN_EXPIRED, "access token expired");
//		}
//
//		if (!jwtUtils.isAccessToken(token)) {
//			throw new BusinessLogicException(ErrorCode.INVALID_TOKEN, "invalid token");
//		}
//		securityUtils.setAuthentication(token);
		return message;
	}
}
