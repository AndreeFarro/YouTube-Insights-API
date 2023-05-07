package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.mothers.dtos.ChannelDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ChannelServiceImplTest {

    @Mock
    private ChannelRepository channelRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ChannelServiceImpl channelService;

    @Test
    void getAll() {
        List<Channel> expectedChannels = ChannelMother.createListChannelsValid();
        given(channelRepository.findAll()).willReturn(expectedChannels);

        List<Channel> actualChannels = channelService.getAll();

        assertNotNull(actualChannels);
        assertThat(actualChannels).isEqualTo(expectedChannels);
        verify(channelRepository).findAll();
    }

    @Test
    void getChannelById() {
        Channel channel = ChannelMother.createChannelValid();
        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));

        Channel expectedChannel = channelService.getChannelById(channel.getId());

        assertThat(expectedChannel).isNotNull();
        verify(channelRepository).findById(channel.getId());
    }

    @Test
    void getChannelById_shouldThrowExceptionEntityNotFound(){
        Long channelId = 1L;

        assertThatThrownBy(() -> channelService.getChannelById(channelId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteById() {
        Channel channel = ChannelMother.createChannelValid();
        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));
        willDoNothing().given(channelRepository).deleteById(channel.getId());

        channelService.deleteById(channel.getId());

        verify(channelRepository,times(1)).deleteById(channel.getId());
    }

    @Test
    void deleteById_shouldThrowExceptionEntityNotFound() {
        Channel channel = ChannelMother.createChannelValid();
        assertThrows(EntityNotFoundException.class,() -> channelService.deleteById(channel.getId()));
    }

    @Test
    void updateChannel() {
        //given
        Channel newChannel = ChannelMother.createChannelValid();

        given(channelRepository.findById(newChannel.getId())).willReturn(Optional.of(newChannel));
        given(channelRepository.save(any(Channel.class))).willReturn(newChannel);

        ChannelDTO channelDTOWithNewData = ChannelDTOMother.createChannelDTOValidFrom(newChannel);

        //when
        Channel updatedChannel = channelService.updateChannel(newChannel.getId(),channelDTOWithNewData);

        //then
        assertThat(updatedChannel).isNotNull();
        assertThat(updatedChannel).isEqualTo(newChannel);
    }

    @Test
    void updateChannel_shouldThrowExceptionEntityNotFound() {
        Channel channel = ChannelMother.createChannelValid();
        ChannelDTO channelDTOWithNewData = ChannelDTOMother.createChannelDTOValidFrom(channel);
        assertThrows(EntityNotFoundException.class,()-> channelService.updateChannel(channel.getId(),channelDTOWithNewData));
    }

    @Test
    void createChannel() {
        Channel channel = ChannelMother.createChannelValid();
        ChannelDTO channelDTO = ChannelDTOMother.createChannelDTOValidFrom(channel);

        given(mapper.map(channelDTO, Channel.class)).willReturn(channel);
        given(channelRepository.save(channel)).willReturn(channel);

        Channel createdChannel = channelService.createChannel(channelDTO);

        assertThat(createdChannel).isNotNull();
        assertThat(createdChannel.getName()).isEqualTo(channel.getName());
    }

    @Test
    void isValidChannelByHandler() {
        Channel channel = ChannelMother.createChannelValid();
        given(channelRepository.findByHandler(channel.getHandler())).willReturn(Optional.of(channel));

        boolean notExistsChannel = channelService.isValidChannelByHandler(channel.getHandler());

        assertFalse(notExistsChannel);
    }
}