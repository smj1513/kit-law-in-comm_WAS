package kit.se.capstone2.chat.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WebSocketDisConnectionHandler implements CommandHandler{
	@Override
	public void handle(StompHeaderAccessor accessor, StompCommand command) {
		SecurityContextHolder.clearContext();
//		accessor.setUser(null);메시지 헤더를 수정하려고 할 때 이미 immutable 상태가 되어있기 때문.
	}

	@Override
	public boolean supports(StompCommand command) {
		return StompCommand.DISCONNECT.equals(command);
	}
}
