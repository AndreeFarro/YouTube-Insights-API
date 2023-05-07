package com.project.apiyoutubeinterplay.mothers.models;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Link;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ChannelMother {
    private static final Faker faker = new Faker();

    public static Channel createChannelValid(){
        return Channel.builder()
                .id(faker.random().nextLong())
                .name(faker.name().firstName().trim())
                .type(faker.internet().emailAddress().trim())
                .description(faker.animal().toString().trim())
                .handler(faker.internet().emailAddress().trim())
                .videos(new ArrayList<>())
                .follows(new ArrayList<>())
                .links(new ArrayList<>())
                .build();
    }

    public static List<Channel> createListChannelsValid(){
        List<Channel> channels = new ArrayList<>();

        channels.add(createChannelValid());
        channels.add(createChannelValid());
        channels.add(createChannelValid());

        return channels;
    }

}
