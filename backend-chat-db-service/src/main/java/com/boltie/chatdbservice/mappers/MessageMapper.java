package com.boltie.chatdbservice.mappers;

import com.boltie.chatdbservice.dto.MessageDto;
import com.boltie.chatdbservice.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "chatRoomId", source = "chatRoom.id")
    MessageDto toMessageDto(Message message);

}
