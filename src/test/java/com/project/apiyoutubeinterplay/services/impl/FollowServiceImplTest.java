package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.mothers.dtos.FollowDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.FollowMother;
import com.project.apiyoutubeinterplay.repositories.ChannelRepository;
import com.project.apiyoutubeinterplay.repositories.FollowRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowServiceImplTest {
    @Mock
    private FollowRepository followRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private FollowServiceImpl followService;


    @Test
    void getAllFollowByChannelID() {
        Channel channel = ChannelMother.createChannelValid();
        List<Follow> follows = FollowMother.createListFollowsValid(channel);

        given(followRepository.findAllByChannelId(channel.getId())).willReturn(follows);

        List<Follow> actualFollows = followService.getAllFollowByChannelID(channel.getId());

        assertThat(actualFollows).isNotNull();
        assertThat(actualFollows).isEqualTo(follows);
        assertThat(actualFollows).hasSize(follows.size());
    }

    @Test
    void getFollowById() {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);

        given(followRepository.findByIdAndChannelId(follow.getId(),channel.getId())).willReturn(Optional.of(follow));

        Follow actualFollow = followService.getFollowByIdAndChannelId(follow.getId(),channel.getId());

        assertThat(actualFollow).isNotNull();
        assertThat(actualFollow).isEqualTo(follow);
    }

    @Test
    void getFollowById_shouldThrowExceptionEntityNotFound() {
        Channel channel = ChannelMother.createChannelValid();
        Follow follow = FollowMother.createFollowValid(channel);

        assertThatThrownBy(() -> followService.getFollowByIdAndChannelId(follow.getId(),channel.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getChannelById(){
        Channel channel = ChannelMother.createChannelValid();

        given(channelRepository.findById(channel.getId())).willReturn(Optional.of(channel));

        Channel actualChannel = followService.getChannelById(channel.getId());

        assertThat(actualChannel).isNotNull();
        assertEquals(actualChannel,channel);
    }

    @Test
    void getChannelById_shouldThrowExceptionEntityNotFound(){
        Long channelId = 1L;

        assertThatThrownBy(() -> followService.getChannelById(channelId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void createFollow() {
        Channel expectedChannel = ChannelMother.createChannelValid();
        Follow exceptedFollow = FollowMother.createFollowValid(expectedChannel);
        FollowDTO followDTO = FollowDTOMother.createFollowDTOValidFrom(exceptedFollow);

        given(channelRepository.findById(expectedChannel.getId())).willReturn(Optional.of(expectedChannel));
        given(mapper.map(followDTO, Follow.class)).willReturn(exceptedFollow);
        given(followRepository.save(any(Follow.class))).willReturn(exceptedFollow);

        Follow actualFollow = followService.createFollow(expectedChannel.getId(),followDTO);

        assertThat(actualFollow).isNotNull();
        assertThat(actualFollow).isEqualTo(exceptedFollow);
    }

    @Test
    void updateFollow() {

        Channel expectedChannel = ChannelMother.createChannelValid();
        Follow expectedFollow = FollowMother.createFollowValid(expectedChannel);
        FollowDTO followDTO = FollowDTOMother.createFollowDTOValidFrom(expectedFollow);

        given(followRepository.findByIdAndChannelId(expectedFollow.getId(),expectedChannel.getId())).willReturn(Optional.of(expectedFollow));
        given(followRepository.save(expectedFollow)).willReturn(expectedFollow);

        Follow updatedFollow = followService.updateFollow(expectedChannel.getId(),expectedFollow.getId(),followDTO);

        assertThat(updatedFollow).isNotNull();
        assertThat(updatedFollow).isEqualTo(expectedFollow);

    }

    @Test
    void deleteById() {

        Channel expectedChannel = ChannelMother.createChannelValid();
        Follow expectedFollow = FollowMother.createFollowValid(expectedChannel);

        given(followRepository.findByIdAndChannelId(expectedFollow.getId(),expectedChannel.getId())).willReturn(Optional.of(expectedFollow));
        willDoNothing().given(followRepository).delete(expectedFollow);

        followService.deleteById(expectedChannel.getId(),expectedFollow.getId());

        verify(followRepository,times(1)).delete(expectedFollow);

    }
}