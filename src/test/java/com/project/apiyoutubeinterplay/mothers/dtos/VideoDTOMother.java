package com.project.apiyoutubeinterplay.mothers.dtos;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.dtos.VideoDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Video;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

public class VideoDTOMother {
    private static final ModelMapper mapper = new ModelMapper();
    private static final Faker faker = new Faker();

    public static VideoDTO createVideoDTOValid(){
        return VideoDTO.builder()
                .name(faker.name().name())
                .url(faker.internet().url())
                .description(faker.animal().toString())
                .build();
    }

    public static VideoDTO createVideoDTOValidFrom(Video video){
        return mapper.map(video,VideoDTO.class);
    }
}
