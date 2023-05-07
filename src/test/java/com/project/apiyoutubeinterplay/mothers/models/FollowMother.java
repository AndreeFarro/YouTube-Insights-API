package com.project.apiyoutubeinterplay.mothers.models;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;

import java.util.ArrayList;
import java.util.List;

public class FollowMother {
    private static final Faker faker = new Faker();

    public static Follow createFollowValid(Channel channel){
        return Follow.builder()
                .id(faker.random().nextLong())
                .subs(faker.number().numberBetween(0,999999))
                .videoNumber(faker.number().numberBetween(0,999999))
                .views(faker.number().numberBetween(0,999999))
                .channel(channel)
                .build();
    }

    public static List<Follow> createListFollowsValid(Channel channel) {
        List<Follow> followList = new ArrayList<>();

        followList.add(createFollowValid(channel));
        followList.add(createFollowValid(channel));
        followList.add(createFollowValid(channel));

        return followList;
    }
}
