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
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String streamKey;

    private String streamUrl;

    @OneToOne(mappedBy = "stream", fetch = FetchType.EAGER)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
