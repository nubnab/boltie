package com.boltie.chatdbservice.dto;

import java.time.OffsetDateTime;

public record MessageDto(Long chatRoomId,
                         Long senderId,
                         String senderName,
                         String content,
                         OffsetDateTime sentAt) {

}
