package com.project.apiyoutubeinterplay.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apiyoutubeinterplay.dtos.VideoDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.mothers.dtos.VideoDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.VideoMother;
import com.project.apiyoutubeinterplay.services.impl.VideoServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoController.class)
class VideoControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private VideoServiceImpl videoService;

    @Autowired
    public VideoControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void getAllVideos_itShouldReturnListOfVideos_WhenVideosExists() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        List<Video> videos = VideoMother.createVideoListValid(channel);

        given(videoService.getAllVideosByChannelId(channel.getId())).willReturn(videos);

        ResultActions resultActions = mockMvc.perform(get("/channel/{channelId}/video", channel.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(videoService).getAllVideosByChannelId(channel.getId());
    }

    @Test
    void getVideo_itShouldReturnVideo_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);

        given(videoService.getByVideoIdAndChannelId(video.getId(), channel.getId())).willReturn(video);

        ResultActions resultActions = mockMvc.perform(get("/channel/{channelId}/video/{videoId}", channel.getId(), video.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(videoService).getByVideoIdAndChannelId(video.getId(), channel.getId());
    }


    @Test
    void getVideo_itShouldReturn404_WhenVideoNotFound() throws Exception {
        Long channelId = 1L;
        Long videoId = 2L;

        given(videoService.getByVideoIdAndChannelId(videoId, channelId))
                .willThrow(new EntityNotFoundException("Video Not Found with id: " + videoId));

        ResultActions resultActions = mockMvc.perform(get("/channel/{channelId}/video/{videoId}", channelId, videoId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        verify(videoService).getByVideoIdAndChannelId(videoId, channelId);
    }

    @Test
    void createVideo_itShouldReturnVideo_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        VideoDTO videoDTO = VideoDTOMother.createVideoDTOValidFrom(video);

        given(videoService.createVideo(channel.getId(), videoDTO)).willReturn(video);

        ResultActions resultActions = mockMvc.perform(post("/channel/{channelId}/video", channel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoDTO)));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(videoService).createVideo(channel.getId(), videoDTO);
    }

    @Test
    void createVideo_itShouldReturn404_WhenChannelNotFound() throws Exception {
        Long channelId = 1L;
        Long videoId = 2L;
        VideoDTO videoDTO = VideoDTOMother.createVideoDTOValid();

        given(videoService.createVideo(channelId, videoDTO))
                .willThrow(new EntityNotFoundException("Channel Not Found with id: " + channelId));

        ResultActions resultActions = mockMvc.perform(post("/channel/{channelId}/video", channelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(videoService).createVideo(channelId, videoDTO);
    }

    @Test
    void updateVideo_itShouldReturnUpdatedVideo_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        VideoDTO videoDTO = VideoDTOMother.createVideoDTOValidFrom(video);

        given(videoService.updateVideo(channel.getId(), video.getId(), videoDTO)).willReturn(video);

        ResultActions resultActions = mockMvc.perform(put("/channel/{channelId}/video/{videoId}", channel.getId(), video.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoDTO)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(videoService).updateVideo(channel.getId(), video.getId(), videoDTO);
    }


    @Test
    void updateVideo_itShouldReturn404_WhenVideoNotFound() throws Exception {
        Long channelId = 1L;
        Long videoId = 2L;
        VideoDTO videoDTO = VideoDTOMother.createVideoDTOValid();

        given(videoService.updateVideo(channelId, videoId, videoDTO))
                .willThrow(new EntityNotFoundException("Video Not Found with id: " + videoId));

        ResultActions resultActions = mockMvc.perform(put("/channel/{channelId}/video/{videoId}", channelId, videoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(videoDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(videoService).updateVideo(channelId, videoId, videoDTO);
    }

    @Test
    void deleteVideo_itShouldNotReturnThrowException_WhenVideoExists() throws Exception {
        Long channelId = 1L;
        Long videoId = 2L;

        willDoNothing().given(videoService).deleteById(channelId, videoId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{channelId}/video/{videoId}", channelId, videoId));

        resultActions.andExpect(status().isNoContent());
        verify(videoService).deleteById(channelId, videoId);
    }

    @Test
    void deleteVideo_itShouldReturnVideo_WhenVideoNotFound() throws Exception {
        Long channelId = 1L;
        Long videoId = 2L;

        willThrow(new EntityNotFoundException("Video Not Found with id: " + videoId))
                .given(videoService).deleteById(channelId, videoId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{channelId}/video/{videoId}", channelId, videoId));

        resultActions.andExpect(status().isNotFound());
        verify(videoService).deleteById(channelId, videoId);
    }
}