package com.boltie.chatdbservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private OffsetDateTime sentAt;

    @Column(nullable = false)
    private boolean hidden;

    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

}
