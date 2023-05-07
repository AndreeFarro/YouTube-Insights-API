package com.project.apiyoutubeinterplay.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.mothers.dtos.FollowDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.FollowMother;
import com.project.apiyoutubeinterplay.services.impl.FollowServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
class FollowControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private FollowServiceImpl followService;

    @Autowired
    public FollowControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void getAllFollowers_itShouldReturnListOfFollows_WhenChannelsExists() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        List<Follow> follows = FollowMother.createListFollowsValid(channel);

        given(followService.getAllFollowByChannelID(channel.getId())).willReturn(follows);

        ResultActions resultActions = mockMvc.perform(get("/channel/{id}/follow",channel.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getFollow_itShouldReturnFollow_WhenFollowExists() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);

        given(followService.getFollowByIdAndChannelId(follow.getId(),channel.getId())).willReturn(follow);

        ResultActions resultActions = mockMvc.perform(get("/channel/{id}/follow/{followId}",channel.getId(),follow.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void getFollow_itShouldReturn404_WhenFollowNotFound() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);

        given(followService.getFollowByIdAndChannelId(follow.getId(),channel.getId()))
                .willThrow(new EntityNotFoundException("Follow Not Found with id: "+ follow.getId()));

        ResultActions resultActions = mockMvc.perform(get("/channel/{id}/follow/{followId}",channel.getId(),follow.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void createFollow_itShouldReturnFollow_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);
        FollowDTO followDTO = FollowDTOMother.createFollowDTOValidFrom(follow);

        given(followService.createFollow(channel.getId(),followDTO)).willReturn(follow);

        ResultActions resultActions = mockMvc.perform(post("/channel/{id}/follow",channel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(followDTO)));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(followService).createFollow(channel.getId(),followDTO);
    }

    @Test
    void createFollow_itShouldReturn404_WhenChannelNotFound() throws Exception {
        Long channelId = 1L;
        FollowDTO followDTO = FollowDTOMother.createFollowDTOValid();

        given(followService.createFollow(channelId,followDTO))
                .willThrow(new EntityNotFoundException("Channel Not Found with id: "+ channelId));

        ResultActions resultActions = mockMvc.perform(post("/channel/{id}/follow",channelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(followDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(followService).createFollow(channelId,followDTO);
    }

    @Test
    void updateFollow_itShouldReturnUpdatedFollow_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);
        FollowDTO followDTO = FollowDTOMother.createFollowDTOValidFrom(follow);

        given(followService.updateFollow(channel.getId(), follow.getId(),followDTO)).willReturn(follow);

        ResultActions resultActions = mockMvc.perform(put("/channel/{id}/follow/{followId}",channel.getId(), follow.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(followDTO)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(followService).updateFollow(channel.getId(), follow.getId(),followDTO);
    }

    @Test
    void updateFollow_itShouldReturn404_WhenUpdateNotFound() throws Exception {
        Long channelId = 1L;
        Long followId = 2L;
        FollowDTO followDTO = FollowDTOMother.createFollowDTOValid();

        given(followService.updateFollow(channelId, followId,followDTO))
                .willThrow(new EntityNotFoundException("Channel Not Found with id: "+ channelId));

        ResultActions resultActions = mockMvc.perform(put("/channel/{id}/follow/{followId}",channelId, followId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(followDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(followService).updateFollow(channelId, followId,followDTO);
    }

    @Test
    void deleteFollow_itShouldNotThrowException_WhenFollowExists() throws Exception {
        Long channelId = 1L;
        Long followId = 2L;
        willDoNothing().given(followService).deleteById(channelId,followId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{id}/follow/{followId}",channelId,followId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNoContent());

        verify(followService).deleteById(channelId,followId);
    }

    @Test
    void deleteFollow_itShouldReturn404_WhenFollowNotFound() throws Exception {
        Long channelId = 1L;
        Long followId = 2L;
        willThrow(new EntityNotFoundException("Follow Not Found with id: "+followId))
                .given(followService).deleteById(channelId,followId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{id}/follow/{followId}",channelId,followId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        verify(followService).deleteById(channelId,followId);
    }
}