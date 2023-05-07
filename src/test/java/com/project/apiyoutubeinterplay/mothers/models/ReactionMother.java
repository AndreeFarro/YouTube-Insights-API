package com.project.apiyoutubeinterplay.mothers.models;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.models.Video;

import java.util.ArrayList;
import java.util.List;

public class ReactionMother {
    private final static Faker faker = new Faker();

    public static Reaction createReactionValid(Video video){
        return Reaction.builder()
                .id(faker.random().nextLong())
                .views(faker.number().numberBetween(0,99999))
                .coments(faker.number().numberBetween(0,99999))
                .likes(faker.number().numberBetween(0,99999))
                .video(video)
                .build();
    }

    public static List<Reaction> createReactionListValid(Video video){
        List<Reaction> reactionList = new ArrayList<>();

        reactionList.add(createReactionValid(video));
        reactionList.add(createReactionValid(video));
        reactionList.add(createReactionValid(video));

        return reactionList;
    }
}
