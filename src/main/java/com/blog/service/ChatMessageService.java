package com.blog.service;


import com.blog.entity.ChatMessage;
import com.blog.repository.ChatMessageRepository;
import com.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final Set<String> connectedUsers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    public void saveMessage(com.blog.dto.ChatMessage message) {
        ChatMessage entity = new ChatMessage();
        entity.setSender(message.getSender());
        entity.setRecipient(message.getRecipient());
        entity.setContent(message.getContent());
        entity.setType(message.getType());
        chatMessageRepository.save(entity);
    }

    public List<com.blog.dto.ChatMessage> getConversation(String user1, String user2) {
        return chatMessageRepository.findConversation(user1, user2)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private com.blog.dto.ChatMessage convertToDTO(ChatMessage entity) {
        com.blog.dto.ChatMessage message = new com.blog.dto.ChatMessage();
        message.setSender(entity.getSender());
        message.setRecipient(entity.getRecipient());
        message.setContent(entity.getContent());
        message.setType(entity.getType());
        return message;
    }
}
