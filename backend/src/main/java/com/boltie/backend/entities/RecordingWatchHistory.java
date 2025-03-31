package com.boltie.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@Builder
public class RecordingWatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User viewedBy;

    @ManyToOne
    @JoinColumn(name = "recording_id")
    private Recording recording;

}
