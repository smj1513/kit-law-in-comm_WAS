package kit.se.capstone2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@EnableWebSocket
@EnableWebSocketMessageBroker // 송신자 -> (메시지 브로커) -> 수신자1, 수신자2 ...
@Configuration
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99) // Spring Security보다 먼저 실행
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final ChannelInterceptor stompInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// stomp 접속 주소 url = ws://{host}:8080/ws-stomp
		registry.addEndpoint("/ws-stomp")
				.setAllowedOriginPatterns("*")
				.withSockJS();
	}


	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/sub"); //스프링이 제공하는 인메모리 메시지 브로커 사용
		registry.setApplicationDestinationPrefixes("/pub"); //메시지를 퍼블리싱하는 프리픽스 지정
	}
//
//	@Override
//	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//		registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
//			@Override
//			public WebSocketHandler decorate(@Autowired WebSocketHandler webSocketHandler) {
//				return new CustomWebSocketHandlerDecorator(webSocketHandler);
//			}
//		});
//	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompInterceptor);
	}

}
