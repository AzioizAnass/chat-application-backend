package com.blog.controller;

import com.blog.dto.ChatMessage;
import com.blog.service.ChatMessageService;
import com.blog.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatResource {
    private final WebSocketService webSocketService;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        webSocketService.handlePrivateMessage(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {
        webSocketService.handleUserConnection(chatMessage);
    }

    @MessageMapping("/chat.disconnect")
    public void disconnect(@Payload ChatMessage chatMessage) {
        webSocketService.handleUserDisconnection(chatMessage);
    }

    @GetMapping("/api/messages/{user1}/{user2}")
    @ResponseBody
    public List<ChatMessage> getConversation(@PathVariable String user1, @PathVariable String user2) {
        return chatMessageService.getConversation(user1, user2);
    }
}
