package banzzac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP endpoint 등록
        registry.addEndpoint("/api/chat").withSockJS();
        
        System.out.println("1");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // "/topic"으로 시작하는 메시지가 메모리 내 브로커로 라우팅되도록 설정
    	config.enableSimpleBroker("/topic", "/queue");
        System.out.println("2");
        // 클라이언트가 "/app"으로 시작하는 대상 메시지를 받도록 설정
        config.setApplicationDestinationPrefixes("/app");
        System.out.println("3");
    }
}
