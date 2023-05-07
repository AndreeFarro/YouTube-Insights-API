package com.project.apiyoutubeinterplay.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int views;

    @Column
    private int coments;

    @Column
    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Video video;
}
