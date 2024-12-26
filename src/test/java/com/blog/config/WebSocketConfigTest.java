package com.blog.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WebSocketConfigTest {

    @Mock
    private StompEndpointRegistry stompEndpointRegistry;

    @Mock
    private MessageBrokerRegistry messageBrokerRegistry;

    @Mock
    private StompWebSocketEndpointRegistration registration;

    @InjectMocks
    private WebSocketConfig webSocketConfig;

    @Test
    void registerStompEndpoints_ShouldConfigureEndpoint() {
        // Arrange
        when(stompEndpointRegistry.addEndpoint("/ws")).thenReturn(registration);
        when(registration.setAllowedOrigins("*")).thenReturn(registration);

        // Act
        webSocketConfig.registerStompEndpoints(stompEndpointRegistry);

        // Assert
        verify(stompEndpointRegistry).addEndpoint("/ws");
        verify(registration).setAllowedOrigins("*");
        verify(registration).withSockJS();
    }

    @Test
    void configureMessageBroker_ShouldConfigureBroker() {
        // Act
        webSocketConfig.configureMessageBroker(messageBrokerRegistry);

        // Assert
        verify(messageBrokerRegistry).setApplicationDestinationPrefixes("/app");
        verify(messageBrokerRegistry).enableSimpleBroker("/chatroom", "/user");
        verify(messageBrokerRegistry).setUserDestinationPrefix("/user");
    }
}
