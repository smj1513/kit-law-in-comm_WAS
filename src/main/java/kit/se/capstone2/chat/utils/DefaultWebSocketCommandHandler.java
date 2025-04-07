package kit.se.capstone2.chat.utils;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class DefaultWebSocketCommandHandler implements CommandHandler {
	@Override
	public void handle(StompHeaderAccessor accessor, StompCommand command) {
		Principal currentAuth = accessor.getUser();
		if (currentAuth instanceof Authentication) {
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication((Authentication) currentAuth);
			SecurityContextHolder.setContext(context);
		}
	}

	@Override
	public boolean supports(StompCommand command) {
		return !(StompCommand.CONNECT.equals(command) ||
				StompCommand.DISCONNECT.equals(command));
	}
}
