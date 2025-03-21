package com.boltie.chatdbservice.services;

import com.boltie.chatdbservice.dto.MessageDto;
import com.boltie.chatdbservice.entities.ChatRoom;
import com.boltie.chatdbservice.entities.Message;
import com.boltie.chatdbservice.mappers.MessageMapper;
import com.boltie.chatdbservice.repositories.ChatRoomRepository;
import com.boltie.chatdbservice.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ChatRoomRepository chatRoomRepository;

   public List<MessageDto> getRecentMessages(Long chatId) {
       Optional<List<Message>> recentMessages =
               messageRepository.findFirst50ByChatRoom_IdOrderByIdDesc(chatId);

       return recentMessages
               .map(this::messagesToDto)
               .orElseGet(ArrayList::new);
   }

   private List<MessageDto> messagesToDto(List<Message> messages) {
       List<MessageDto> messageDtoList = new ArrayList<>();

       messages
               .forEach(message ->
                   messageDtoList.add(messageMapper.toMessageDto(message)));

       return messageDtoList;
   }

    @RabbitListener(queues = "chat.message.queue")
    void receivedMessage(MessageDto messageDto) {

        Optional<ChatRoom> firstByChatOwnerId = chatRoomRepository
                .findFirstByChatOwnerId(messageDto.chatRoomId());

        if (firstByChatOwnerId.isPresent()) {
            ChatRoom chatRoom = firstByChatOwnerId.get();

            messageRepository.save(
                    Message.builder()
                        .senderId(messageDto.senderId())
                        .senderName(messageDto.senderName())
                        .content(messageDto.content())
                        .sentAt(messageDto.sentAt())
                        .hidden(false)
                        .chatRoom(chatRoom)
                        .build());

            log.info("Received message {}", messageDto);
        }


    }
}
