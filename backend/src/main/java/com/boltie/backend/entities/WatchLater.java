package com.boltie.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@Builder
public class WatchLater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User savedBy;

    @ManyToOne
    @JoinColumn(name = "recording_id")
    private Recording recording;

}
