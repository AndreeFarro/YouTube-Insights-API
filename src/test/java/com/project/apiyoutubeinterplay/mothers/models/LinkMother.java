package com.project.apiyoutubeinterplay.mothers.models;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.models.Link;

import java.util.ArrayList;
import java.util.List;

public class LinkMother {
    private static final Faker faker = new Faker();

    public static Link createLinkValid(Channel channel) {
        return Link.builder()
                .id(faker.random().nextLong())
                .name(faker.name().name())
                .uri(faker.internet().url())
                .channel(channel)
                .build();
    }

    public static List<Link> createListLinksValid(Channel channel) {
        List<Link> linkList = new ArrayList<>();

        linkList.add(createLinkValid(channel));
        linkList.add(createLinkValid(channel));
        linkList.add(createLinkValid(channel));

        return linkList;
    }
}
