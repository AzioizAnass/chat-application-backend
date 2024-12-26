package com.blog.config;

import com.blog.dto.ChatMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WebSocketEventListenerTest {

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @Mock
    private SessionDisconnectEvent disconnectEvent;

    @Mock
    private Message<byte[]> message;

    @Mock
    private StompHeaderAccessor headerAccessor;

    @Captor
    private ArgumentCaptor<ChatMessage> chatMessageCaptor;

    @InjectMocks
    private WebSocketEventListener webSocketEventListener;

    @Test
    void handleWebSocketDisconnectListener_WithValidUsername_ShouldSendLeaveMessage() {
        // Arrange
        String username = "testUser";
        Map<String, Object> sessionAttributes = new HashMap<>();
        sessionAttributes.put("username", username);

        when(disconnectEvent.getMessage()).thenReturn(message);
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        try (var mocked = mockStatic(StompHeaderAccessor.class)) {
            mocked.when(() -> StompHeaderAccessor.wrap(any(Message.class)))
                    .thenReturn(headerAccessor);

            // Act
            webSocketEventListener.handleWebSocketDisconnectListener(disconnectEvent);

            // Assert
            verify(messagingTemplate).convertAndSend(
                    eq("/chatroom/public"),
                    chatMessageCaptor.capture()
            );

            ChatMessage sentMessage = chatMessageCaptor.getValue();
            assertEquals(ChatMessage.MessageType.LEAVE, sentMessage.getType());
            assertEquals(username, sentMessage.getSender());
        }
    }

    @Test
    void handleWebSocketDisconnectListener_WithoutUsername_ShouldNotSendMessage() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        
        when(disconnectEvent.getMessage()).thenReturn(message);
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        try (var mocked = mockStatic(StompHeaderAccessor.class)) {
            mocked.when(() -> StompHeaderAccessor.wrap(any(Message.class)))
                    .thenReturn(headerAccessor);

            // Act
            webSocketEventListener.handleWebSocketDisconnectListener(disconnectEvent);

            // Assert
            verify(messagingTemplate, never()).convertAndSend(
                    anyString(),
                    any(ChatMessage.class)
            );
        }
    }
}
