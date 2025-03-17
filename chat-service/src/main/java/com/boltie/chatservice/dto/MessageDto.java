package com.boltie.chatservice.dto;

import java.io.Serializable;

public record MessageDto(String sender, String content) implements Serializable {
}
