package com.project.apiyoutubeinterplay.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.apiyoutubeinterplay.dtos.LinkDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Link;
import com.project.apiyoutubeinterplay.mothers.dtos.LinkDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.LinkMother;
import com.project.apiyoutubeinterplay.services.impl.LinkServiceImpl;
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

@WebMvcTest(LinkController.class)
class LinkControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private LinkServiceImpl linkService;

    @Autowired
    LinkControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void getAllLinks_itShouldReturnListOfLinks_WhenLinksExists() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        List<Link> links = LinkMother.createListLinksValid(channel);

        given(linkService.getALlLinksByChannelId(channel.getId())).willReturn(links);

        ResultActions resultActions = mockMvc.perform(get("/channel/{channelId}/link", channel.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getLink_itShouldReturnLink_WhenLinkExists() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Link link = LinkMother.createLinkValid(channel);

        given(linkService.getLinkByIdAndChannelId(link.getId(), channel.getId())).willReturn(link);

        ResultActions resultActions = mockMvc.perform(get("/channel/{channelId}/link/{linkId}", channel.getId(), link.getId())
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(linkService).getLinkByIdAndChannelId(link.getId(), channel.getId());
    }

    @Test
    void getLink_itShouldReturn404_WhenLinkNotFound() throws Exception {
        Long channelId = 1L;
        Long linkId = 2L;

        given(linkService.getLinkByIdAndChannelId(linkId, channelId))
                .willThrow(new EntityNotFoundException("Link Not Found with id: " + channelId));

        ResultActions resultActions = mockMvc.perform(get("/channel/{channelId}/link/{linkId}", channelId, linkId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        verify(linkService).getLinkByIdAndChannelId(linkId, channelId);
    }

    @Test
    void createLink_itShouldReturnLink_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Link link = LinkMother.createLinkValid(channel);
        LinkDTO linkDTO = LinkDTOMother.createLinkDTOValid();

        given(linkService.createLink(channel.getId(), linkDTO)).willReturn(link);

        ResultActions resultActions = mockMvc.perform(post("/channel/{id}/link", channel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(linkDTO)));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(linkService).createLink(channel.getId(), linkDTO);
    }

    @Test
    void createLink_itShouldReturn404_WhenChannelNotFound() throws Exception {
        Long channelId = 1L;
        LinkDTO linkDTO = LinkDTOMother.createLinkDTOValid();

        given(linkService.createLink(channelId, linkDTO))
                .willThrow(new EntityNotFoundException("Channel Not Found with id: " + channelId));

        ResultActions resultActions = mockMvc.perform(post("/channel/{id}/link", channelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(linkDTO)));

        resultActions.andExpect(status().isNotFound());

        verify(linkService).createLink(channelId, linkDTO);
    }

    @Test
    void updateLink_itShouldReturnUpdateLink_WhenRequestIsValid() throws Exception {
        Channel channel = ChannelMother.createChannelValid();
        Link link = LinkMother.createLinkValid(channel);
        LinkDTO linkDTO = LinkDTOMother.createLinkDTOValidFrom(link);

        given(linkService.updateLink(channel.getId(), link.getId(), linkDTO)).willReturn(link);

        ResultActions resultActions = mockMvc.perform(put("/channel/{channelId}/link/{linkId}", channel.getId(), link.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(linkDTO)));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(linkService).updateLink(channel.getId(), link.getId(), linkDTO);
    }


    @Test
    void updateLink_itShouldReturn404_WhenLinkNotFound() throws Exception {
        Long channelId = 1L;
        Long linkId = 2L;
        LinkDTO linkDTO = LinkDTOMother.createLinkDTOValid();

        given(linkService.updateLink(channelId, linkId, linkDTO))
                .willThrow(new EntityNotFoundException("Link Not Found with id: " + linkId));

        ResultActions resultActions = mockMvc.perform(put("/channel/{channelId}/link/{linkId}", channelId, linkId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(linkDTO)));

        resultActions.andExpect(status().isNotFound());
        verify(linkService).updateLink(channelId, linkId, linkDTO);
    }

    @Test
    void deleteLink_itShouldNotThrowException_WhenLinkExists() throws Exception {
        Long channelId = 1L;
        Long linkId = 2L;
        willDoNothing().given(linkService).deleteById(channelId, linkId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{channelId}/link/{linkId}", channelId, linkId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNoContent());
        verify(linkService).deleteById(channelId, linkId);
    }

    @Test
    void deleteLink_itShouldReturn404_WhenLinkNotFound() throws Exception {
        Long channelId = 1L;
        Long linkId = 2L;
        willThrow(new EntityNotFoundException("Link Not Found with id: " + linkId))
                .given(linkService).deleteById(channelId, linkId);

        ResultActions resultActions = mockMvc.perform(delete("/channel/{channelId}/link/{linkId}", channelId, linkId)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());

        verify(linkService).deleteById(channelId, linkId);
    }
}