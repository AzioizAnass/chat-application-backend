package com.blog.controller;

import com.blog.dto.ChatMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Mock
    private SimpMessageHeaderAccessor headerAccessor;

    private ChatMessage chatMessage;

    @BeforeEach
    void setUp() {
        chatMessage = ChatMessage.builder()
                .type(ChatMessage.MessageType.CHAT)
                .sender("testUser")
                .content("Hello, World!")
                .time("12:00")
                .build();
    }

    @Test
    void sendMessage_ShouldReturnSameMessage() {
        ChatMessage result = chatController.sendMessage(chatMessage);
        
        assertNotNull(result);
        assertEquals(chatMessage.getContent(), result.getContent());
        assertEquals(chatMessage.getSender(), result.getSender());
        assertEquals(chatMessage.getType(), result.getType());
        assertEquals(chatMessage.getTime(), result.getTime());
    }

    @Test
    void addUser_ShouldAddUserToSessionAndReturnMessage() {
        // Arrange
        Map<String, Object> sessionAttributes = new HashMap<>();
        when(headerAccessor.getSessionAttributes()).thenReturn(sessionAttributes);
        
        chatMessage.setType(ChatMessage.MessageType.JOIN);

        // Act
        ChatMessage result = chatController.addUser(chatMessage, headerAccessor);

        // Assert
        assertNotNull(result);
        assertEquals(chatMessage.getSender(), result.getSender());
        assertEquals(ChatMessage.MessageType.JOIN, result.getType());
        assertEquals(chatMessage.getSender(), sessionAttributes.get("username"));
        verify(headerAccessor, times(1)).getSessionAttributes();
    }
}
