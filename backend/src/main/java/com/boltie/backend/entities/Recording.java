package com.boltie.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userRecordingTrackingId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String folderName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void setUserRecordingTrackingId() {
        if (this.user != null && this.userRecordingTrackingId == null) {
            this.userRecordingTrackingId = this.user.getRecordings().size() + 1;
        }
    }
}
