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
		accessor.setUser(null);
	}

	@Override
	public boolean supports(StompCommand command) {
		return StompCommand.DISCONNECT.equals(command);
	}
}
