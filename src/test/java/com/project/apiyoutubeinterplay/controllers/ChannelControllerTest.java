package com.project.apiyoutubeinterplay.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.mothers.dtos.ChannelDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.services.impl.ChannelServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChannelController.class)
class ChannelControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public ChannelControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @MockBean
    private ChannelServiceImpl channelService;


    @Test
    void getChannel_itShouldReturnChannel_WhenChannelExists() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        given(channelService.getChannelById(channel.getId())).willReturn(channel);

        ResultActions resultActions = mockMvc.perform(get("/channel/{id}",channel.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                     .andExpect(jsonPath("$.id").value(channel.getId()));

        verify(channelService, times(1)).getChannelById(channel.getId());
    }

    @Test
    void getChannel_itShouldReturn404_WhenChannelNotFound() throws Exception {
        Long channelId = 1L;
        given(channelService.getChannelById(channelId))
                .willThrow(new EntityNotFoundException("Channel Not Found with id: "+ channelId));

        ResultActions resultActions = mockMvc.perform(get("/channel/{id}",channelId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        verify(channelService, times(1)).getChannelById(channelId);
    }

    @Test
    void getAllChannel_itShouldReturnListOfChannels_WhenChannelsExists() throws Exception {
        List<Channel> channels = ChannelMother.createListChannelsValid();
        given(channelService.getAll()).willReturn(channels);

        ResultActions resultActions = mockMvc.perform(get("/channel")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                     .andExpect(jsonPath("$",hasSize(channels.size())));

        verify(channelService,times(1)).getAll();
    }

    @Test
    void createChannel_itShouldReturnChannel_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        ChannelDTO channelDTO = ChannelDTOMother.createChannelDTOValidFrom(channel);

        given(channelService.isValidChannelByHandler(channelDTO.getHandler())).willReturn(true);
        given(channelService.createChannel(channelDTO)).willReturn(channel);

        ResultActions resultActions = mockMvc.perform(post("/channel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(channelDTO)));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(channelService).createChannel(channelDTO);
    }

    @Test
    void createChannel_itShouldReturn400_WhenBadRequest() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        ChannelDTO channelDTO = ChannelDTOMother.createChannelDTOValidFrom(channel);

        given(channelService.isValidChannelByHandler(channelDTO.getHandler())).willReturn(false);

        ResultActions resultActions = mockMvc.perform(post("/channel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(channelDTO)));

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void deleteChannel_itShouldNotReturnThrowException_WhenChannelExists() throws Exception {
        Long channelId = 1L;
        willDoNothing().given(channelService).deleteById(channelId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{id}",channelId));

        resultActions.andExpect(status().isNoContent());
        verify(channelService).deleteById(channelId);
    }
    @Test
    void deleteChannel_itShouldReturn404_WhenChannelNotFound() throws Exception {
        Long channelId = 1L;
        willThrow(new EntityNotFoundException("Channel Not Found with id: "+ channelId))
                .given(channelService).deleteById(channelId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{id}",channelId));

        resultActions.andExpect(status().isNotFound());
        verify(channelService).deleteById(channelId);
    }

    @Test
    void updateChannel_itShouldReturnUpdatedChannel_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        ChannelDTO channelDTO = ChannelDTOMother.createChannelDTOValidFrom(channel);

        given(channelService.isValidChannelByHandler(channelDTO.getHandler())).willReturn(true);
        given(channelService.updateChannel(channel.getId(),channelDTO)).willReturn(channel);

        ResultActions resultActions = mockMvc.perform(put("/channel/{id}", channel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(channelDTO)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(channel.getId()));

        verify(channelService).updateChannel(channel.getId(),channelDTO);
    }
}