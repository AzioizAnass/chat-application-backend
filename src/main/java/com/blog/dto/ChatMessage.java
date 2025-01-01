package com.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private String content;
    private String sender;
    private String recipient;
    private MessageType type;
    private LocalDateTime timestamp;
    public enum MessageType {
        CHAT, JOIN, LEAVE, USERS_LIST
    }
}
