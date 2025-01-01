package com.blog.chat;

import com.blog.dto.ChatMessage;
import com.blog.service.WebSocketService;
import com.blog.service.ChatMessageService;
import com.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private ChatMessageService chatMessageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private WebSocketService webSocketService;

    @Test
    void shouldHandleUserConnection() {
        // User 1 joins
        ChatMessage joinMessage = ChatMessage.builder()
                .sender("User1")
                .type(ChatMessage.MessageType.JOIN)
                .timestamp(LocalDateTime.now())
                .build();

        webSocketService.handleUserConnection(joinMessage);
        verify(userService).addUser("User1");
        verify(messagingTemplate).convertAndSend(eq("/topic/public"), any(ChatMessage.class));
    }

    @Test
    void shouldHandlePrivateMessage() {
        // User 1 sends private message
        ChatMessage chatMessage = ChatMessage.builder()
                .sender("User1")
                .recipient("User2")
                .content("Hello User2!")
                .type(ChatMessage.MessageType.CHAT)
                .timestamp(LocalDateTime.now())
                .build();

        webSocketService.handlePrivateMessage(chatMessage);
        verify(chatMessageService).saveMessage(chatMessage);
        verify(messagingTemplate).convertAndSend("/user/User2/topic/private", chatMessage);
        verify(messagingTemplate).convertAndSend("/user/User1/topic/private", chatMessage);
    }
}
