package kit.se.capstone2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocket
@EnableWebSocketMessageBroker // 송신자 -> (메시지 브로커) -> 수신자1, 수신자2 ...
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// stomp 접속 주소 url = ws://{host}:8080/ws
		registry.addEndpoint("/ws-stomp")
				.setAllowedOriginPatterns("*");
	//			.withSockJS();
	}


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/sub"); //스프링이 제공하는 인메모리 메시지 브로커 사용
		registry.setApplicationDestinationPrefixes("/pub"); //메시지를 퍼블리싱하는 프리픽스 지정
	}
}
