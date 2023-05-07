package com.project.apiyoutubeinterplay.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apiyoutubeinterplay.dtos.ReactionDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.mothers.dtos.ReactionDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.ReactionMother;
import com.project.apiyoutubeinterplay.mothers.models.VideoMother;
import com.project.apiyoutubeinterplay.services.ReactionService;
import com.project.apiyoutubeinterplay.services.impl.ReactionServiceImpl;
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

@WebMvcTest(ReactionController.class)
class ReactionControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private ReactionServiceImpl reactionService;

    @Autowired
    public ReactionControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void getAllReactions_itShouldReturnListOfReactions_WhenReactionsExists() throws Exception {

        Video video = VideoMother.createVideoValid();
        List<Reaction> reactions = ReactionMother.createReactionListValid(video);

        given(reactionService.getALlReactionsByVideoId(video.getId())).willReturn(reactions);

        ResultActions resultActions = mockMvc.perform(get("/video/{videoId}/reaction",video.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getReaction_itShouldReturnReaction_WhenReactionExist() throws Exception {
        Video video = VideoMother.createVideoValid();
        Reaction reaction = ReactionMother.createReactionValid(video);

        given(reactionService.getReactionById(video.getId(), reaction.getId())).willReturn(reaction);

        ResultActions resultActions = mockMvc.perform(get("/video/{videoId}/reaction/{reactionId}", video.getId(),reaction.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getReaction_itShouldReturn404_WhenReactionNotFound() throws Exception {
        Long videoId = 1L;
        Long reactionId = 2L;

        given(reactionService.getReactionById(videoId, reactionId))
                .willThrow(new EntityNotFoundException("Reaction Not Found with id: "+ reactionId));

        ResultActions resultActions = mockMvc.perform(get("/video/{videoId}/reaction/{reactionId}", videoId, reactionId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
        verify(reactionService).getReactionById(videoId,reactionId);
    }

    @Test
    void createReaction_itShouldReturnReaction_WhenRequestIsValid() throws Exception {
        Video video = VideoMother.createVideoValid();
        Reaction reaction = ReactionMother.createReactionValid(video);
        ReactionDTO reactionDTO = ReactionDTOMother.createReactionDTOValidFrom(reaction);

        given(reactionService.createReaction(video.getId(),reactionDTO)).willReturn(reaction);

        ResultActions resultActions = mockMvc.perform(post("/video/{videoId}/reaction", video.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reactionDTO)));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(reactionService).createReaction(video.getId(),reactionDTO);
    }

    @Test
    void createReaction_itShouldReturn404_WhenVideoNotFound() throws Exception {
        Long videoId = 1L;
        ReactionDTO reactionDTO = ReactionDTOMother.createReactionDTOValid();

        given(reactionService.createReaction(videoId,reactionDTO))
                .willThrow(new EntityNotFoundException("Video Not Found with id: "+ videoId));

        ResultActions resultActions = mockMvc.perform(post("/video/{videoId}/reaction", videoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reactionDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(reactionService).createReaction(videoId,reactionDTO);
    }

    @Test
    void updateReaction_itShouldReturnUpdatedReaction_WhenRequestIsValid() throws Exception {
        Video video = VideoMother.createVideoValid();
        Reaction reaction = ReactionMother.createReactionValid(video);
        ReactionDTO reactionDTO = ReactionDTOMother.createReactionDTOValidFrom(reaction);

        given(reactionService.updateReaction(video.getId(), reaction.getId(),reactionDTO)).willReturn(reaction);

        ResultActions resultActions = mockMvc.perform(put("/video/{videoId}/reaction/{reactionId}", video.getId(), reaction.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reactionDTO)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(reactionService).updateReaction(video.getId(), reaction.getId(),reactionDTO);
    }

    @Test
    void updateReaction_itShouldReturn404_WhenReactionNotFound() throws Exception {
        Long videoId = 1L;
        Long reactionId = 2L;
        ReactionDTO reactionDTO = ReactionDTOMother.createReactionDTOValid();

        given(reactionService.updateReaction(videoId, reactionId,reactionDTO))
                .willThrow(new EntityNotFoundException("Reaction Not Found with id: "+ reactionId));

        ResultActions resultActions = mockMvc.perform(put("/video/{videoId}/reaction/{reactionId}", videoId, reactionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reactionDTO)));

        resultActions.andExpect(status().isNotFound());
        verify(reactionService).updateReaction(videoId, reactionId,reactionDTO);
    }

    @Test
    void deleteReaction_itShouldNotThrowException_WhenReactionExists() throws Exception {
        Long videoId = 1L;
        Long reactionId = 2L;

        willDoNothing().given(reactionService).deleteById(videoId,reactionId);

        ResultActions resultActions = mockMvc.perform(delete("/video/{videoId}/reaction/{reactionId}",videoId,reactionId));

        resultActions.andExpect(status().isNoContent());

        verify(reactionService).deleteById(videoId,reactionId);
    }

    @Test
    void deleteReaction_itShouldReturn404_WhenReactionNotFound() throws Exception {
        Long videoId = 1L;
        Long reactionId = 2L;

        willThrow(new EntityNotFoundException("Reaction Not Found with id: "+reactionId))
                .given(reactionService).deleteById(videoId,reactionId);

        ResultActions resultActions = mockMvc.perform(delete("/video/{videoId}/reaction/{reactionId}",videoId,reactionId));

        resultActions.andExpect(status().isNotFound());

        verify(reactionService).deleteById(videoId,reactionId);
    }
}