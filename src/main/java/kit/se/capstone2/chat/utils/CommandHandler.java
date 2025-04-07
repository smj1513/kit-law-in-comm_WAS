package kit.se.capstone2.chat.utils;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

public interface CommandHandler {

	void handle(StompHeaderAccessor accessor, StompCommand command);

	boolean supports(StompCommand command);
}
