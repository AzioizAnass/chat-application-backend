package com.blog.controller;


import com.blog.dto.ChatMessage;
import com.blog.service.ChatMessageService;
import com.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        log.info("Message reçu : {} -> {}", chatMessage.getSender(), chatMessage.getRecipient());

        if (chatMessage.getRecipient() != null) {
            chatMessageService.saveMessage(chatMessage);

            String recipientDestination = "/user/" + chatMessage.getRecipient() + "/topic/private";
            log.info("Envoi au destinataire : {}", recipientDestination);
            messagingTemplate.convertAndSend(recipientDestination, chatMessage);

            String senderDestination = "/user/" + chatMessage.getSender() + "/topic/private";
            log.info("Envoi à l'expéditeur : {}", senderDestination);
            messagingTemplate.convertAndSend(senderDestination, chatMessage);
        }
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage) {
        String username = chatMessage.getSender();
        log.info("Nouvel utilisateur connecté : {}", username);

        userService.addUser(username);

        ChatMessage usersListMessage = new ChatMessage();
        usersListMessage.setType(ChatMessage.MessageType.USERS_LIST);
        usersListMessage.setContent(String.join(",", userService.getConnectedUsers()));
        messagingTemplate.convertAndSend("/topic/public", usersListMessage);

        log.info("Liste des utilisateurs mise à jour : {}", usersListMessage.getContent());
    }

    @MessageMapping("/chat.disconnect")
    public void disconnect(@Payload ChatMessage chatMessage) {
        log.info("Utilisateur déconnecté : {}", chatMessage.getSender());
        userService.removeUser(chatMessage.getSender());

        ChatMessage usersListMessage = new ChatMessage();
        usersListMessage.setType(ChatMessage.MessageType.USERS_LIST);
        usersListMessage.setContent(String.join(",", userService.getConnectedUsers()));
        messagingTemplate.convertAndSend("/topic/public", usersListMessage);
    }

    @GetMapping("/api/messages/{user1}/{user2}")
    @ResponseBody
    public List<ChatMessage> getConversation(@PathVariable String user1, @PathVariable String user2) {
        return chatMessageService.getConversation(user1, user2);
    }
}
