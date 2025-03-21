package com.boltie.chatdbservice.mappers;

import com.boltie.chatdbservice.dto.MessageDto;
import com.boltie.chatdbservice.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto toMessageDto(Message message);

}
