package com.blog.service;

import com.blog.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    public void handlePrivateMessage(ChatMessage chatMessage) {
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

    public void handleUserConnection(ChatMessage chatMessage) {
        String username = chatMessage.getSender();
        log.info("Nouvel utilisateur connecté : {}", username);

        userService.addUser(username);

        ChatMessage usersListMessage = new ChatMessage();
        usersListMessage.setType(ChatMessage.MessageType.USERS_LIST);
        usersListMessage.setContent(String.join(",", userService.getConnectedUsers()));
        messagingTemplate.convertAndSend("/topic/public", usersListMessage);

        log.info("Liste des utilisateurs mise à jour : {}", usersListMessage.getContent());
    }

    public void handleUserDisconnection(ChatMessage chatMessage) {
        log.info("Utilisateur déconnecté : {}", chatMessage.getSender());
        userService.removeUser(chatMessage.getSender());

        ChatMessage usersListMessage = new ChatMessage();
        usersListMessage.setType(ChatMessage.MessageType.USERS_LIST);
        usersListMessage.setContent(String.join(",", userService.getConnectedUsers()));
        messagingTemplate.convertAndSend("/topic/public", usersListMessage);
    }
}
