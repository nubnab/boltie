package com.boltie.chatservice.mappers;

import com.boltie.chatservice.dto.MessageDto;
import com.boltie.chatservice.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto toMessageDto(Message message);

}
