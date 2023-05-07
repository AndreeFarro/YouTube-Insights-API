package com.project.apiyoutubeinterplay.mothers.models;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoMother {
    private final static Faker faker = new Faker();

    public static Video createVideoValid(Channel channel){
        return Video.builder()
                .id(faker.random().nextLong())
                .name(faker.name().name())
                .url(faker.internet().url())
                .description(faker.animal().toString())
                .reactions(new ArrayList<>())
                .channel(channel)
                .build();
    }
    public static Video createVideoValid(){
        return Video.builder()
                .id(faker.random().nextLong())
                .name(faker.name().name())
                .url(faker.internet().url())
                .description(faker.animal().toString())
                .reactions(new ArrayList<>())
                .build();
    }

    public static List<Video> createVideoListValid(Channel channel){
        List<Video> videoList = new ArrayList<>();

        videoList.add(createVideoValid(channel));
        videoList.add(createVideoValid(channel));
        videoList.add(createVideoValid(channel));

        return videoList;
    }
}
