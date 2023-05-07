package com.project.apiyoutubeinterplay.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String handler;

    @Column
    private String type;

    @Column
    private String description;

    @OneToMany(mappedBy = "channel",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "channel",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Video> videos = new ArrayList<>();
}
