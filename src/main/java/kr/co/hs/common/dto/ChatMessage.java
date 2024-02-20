package kr.co.hs.common.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String receiver;
    private String time;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        GROUP,
        NOTICE
    }

}