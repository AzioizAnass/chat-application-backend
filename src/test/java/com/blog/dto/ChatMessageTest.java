package com.blog.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageTest {

    @Test
    void testChatMessageBuilder() {
        // Arrange & Act
        ChatMessage message = ChatMessage.builder()
                .content("Test message")
                .sender("testUser")
                .type(ChatMessage.MessageType.CHAT)
                .time("12:00")
                .build();

        // Assert
        assertEquals("Test message", message.getContent());
        assertEquals("testUser", message.getSender());
        assertEquals(ChatMessage.MessageType.CHAT, message.getType());
        assertEquals("12:00", message.getTime());
    }

    @Test
    void testChatMessageSettersAndGetters() {
        // Arrange
        ChatMessage message = new ChatMessage();

        // Act
        message.setContent("Test message");
        message.setSender("testUser");
        message.setType(ChatMessage.MessageType.JOIN);
        message.setTime("12:00");

        // Assert
        assertEquals("Test message", message.getContent());
        assertEquals("testUser", message.getSender());
        assertEquals(ChatMessage.MessageType.JOIN, message.getType());
        assertEquals("12:00", message.getTime());
    }

    @Test
    void testEquals() {
        // Arrange
        ChatMessage message1 = ChatMessage.builder()
                .content("Test message")
                .sender("testUser")
                .type(ChatMessage.MessageType.CHAT)
                .time("12:00")
                .build();

        ChatMessage message2 = ChatMessage.builder()
                .content("Test message")
                .sender("testUser")
                .type(ChatMessage.MessageType.CHAT)
                .time("12:00")
                .build();

        // Assert
        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    void testMessageTypes() {
        // Assert
        assertNotNull(ChatMessage.MessageType.values());
        assertEquals(3, ChatMessage.MessageType.values().length);
        assertTrue(java.util.Arrays.asList(ChatMessage.MessageType.values())
                .containsAll(java.util.Arrays.asList(
                        ChatMessage.MessageType.CHAT,
                        ChatMessage.MessageType.JOIN,
                        ChatMessage.MessageType.LEAVE)));
    }
}
