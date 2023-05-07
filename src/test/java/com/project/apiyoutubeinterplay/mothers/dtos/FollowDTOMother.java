package com.project.apiyoutubeinterplay.mothers.dtos;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import org.modelmapper.ModelMapper;

public class FollowDTOMother {
    private static final ModelMapper mapper = new ModelMapper();
    private static final Faker faker = new Faker();

    public static FollowDTO createFollowDTOValid(){
        return FollowDTO.builder()
                .subs(faker.number().numberBetween(0,999999))
                .videoNumber(faker.number().numberBetween(0,999999))
                .views(faker.number().numberBetween(0,999999))
                .build();
    }




    public static FollowDTO createFollowDTOValidFrom(Follow follow){
        return mapper.map(follow,FollowDTO.class);
    }
}
