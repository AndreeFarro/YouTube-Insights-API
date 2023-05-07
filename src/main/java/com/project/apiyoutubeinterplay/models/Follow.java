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
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int subs;

    @Column
    private int views;

    @Column
    private int videoNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Channel channel;
}
