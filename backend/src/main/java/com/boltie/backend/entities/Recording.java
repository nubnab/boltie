package com.boltie.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
@Builder
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    public void setUserRecordingTrackingId() {
        if (this.user != null && this.userRecordingTrackingId == null) {
            this.userRecordingTrackingId = this.user.getRecordings().size() + 1;
        }
    }
}
