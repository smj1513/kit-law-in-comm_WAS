package kit.se.capstone2.chat.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Log4j2
public class StompInterceptor implements ChannelInterceptor {
	private final List<CommandHandler> commandHandlers;

	@Autowired
	public StompInterceptor(WebSocketConnectionHandler webSocketConnectionHandler,
	                        WebSocketDisConnectionHandler webSocketDisConnectionHandler,
	                        DefaultWebSocketCommandHandler defaultWebSocketCommandHandle) {
		this.commandHandlers = List.of(webSocketConnectionHandler, webSocketDisConnectionHandler, defaultWebSocketCommandHandle);
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		StompCommand command = accessor.getCommand();
		for (CommandHandler commandHandler : commandHandlers) {
			if (commandHandler.supports(command)) {
				commandHandler.handle(accessor, command);
				break;
			}
		}
		return message;
	}
}
