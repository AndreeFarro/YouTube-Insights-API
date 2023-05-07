package com.project.apiyoutubeinterplay.mothers.dtos;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

public class ChannelDTOMother {
    private static final ModelMapper mapper = new ModelMapper();
    private static final Faker faker = new Faker();


    public static ChannelDTO createChannelDTOValidFrom(Channel channel){
        return mapper.map(channel,ChannelDTO.class);
    }
}
