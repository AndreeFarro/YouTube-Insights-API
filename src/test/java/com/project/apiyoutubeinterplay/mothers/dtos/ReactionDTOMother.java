package com.project.apiyoutubeinterplay.mothers.dtos;

import com.github.javafaker.Faker;
import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.dtos.ReactionDTO;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.models.Video;
import org.modelmapper.ModelMapper;

public class ReactionDTOMother {
    private final static ModelMapper mapper = new ModelMapper();
    private final static Faker faker = new Faker();

    public static ReactionDTO createReactionDTOValid(){
        return ReactionDTO.builder()
                .views(faker.number().numberBetween(0,99999))
                .coments(faker.number().numberBetween(0,99999))
                .likes(faker.number().numberBetween(0,99999))
                .build();
    }


    public static ReactionDTO createReactionDTOValidFrom(Reaction reaction){
        return mapper.map(reaction,ReactionDTO.class);
    }
}
