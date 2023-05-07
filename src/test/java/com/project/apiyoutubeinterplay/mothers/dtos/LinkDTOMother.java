package com.project.apiyoutubeinterplay.mothers.dtos;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.dtos.LinkDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.models.Link;
import org.modelmapper.ModelMapper;

public class LinkDTOMother {
    private static final ModelMapper mapper = new ModelMapper();
    private static final Faker faker = new Faker();

    public static LinkDTO createLinkDTOValid(){
        return LinkDTO.builder()
                .name(faker.name().name())
                .uri(faker.internet().url())
                .build();
    }

    public static LinkDTO createLinkDTOValidFrom(Link link){
        return mapper.map(link,LinkDTO.class);
    }

}
