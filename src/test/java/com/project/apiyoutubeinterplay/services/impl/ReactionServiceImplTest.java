package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.ReactionDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.mothers.dtos.ReactionDTOMother;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.ReactionMother;
import com.project.apiyoutubeinterplay.mothers.models.VideoMother;
import com.project.apiyoutubeinterplay.repositories.ReactionRepository;
import com.project.apiyoutubeinterplay.repositories.VideoRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReactionServiceImplTest {

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ReactionServiceImpl reactionService;


    @Test
    void getReactionById() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        Reaction reaction = ReactionMother.createReactionValid(video);

        given(reactionRepository.findByIdAndVideoId(
                reaction.getId(),
                video.getId()
        )).willReturn(Optional.of(reaction));

        Reaction actualReaction = reactionService.getReactionById(video.getId(),reaction.getId());

        assertThat(actualReaction).isNotNull();
        assertEquals(actualReaction,reaction);
    }
    @Test
    void getReactionById_shouldThrowExceptionEntityNotFound() {
        Long videoId = 2L;
        Long reactionId = 3L;

        assertThatThrownBy(() -> reactionService.getReactionById(videoId,reactionId))
                .isInstanceOf(EntityNotFoundException.class);

    }

    @Test
    void getByVideoId(){
        Video video = VideoMother.createVideoValid();

        given(videoRepository.findById(video.getId())).willReturn(Optional.of(video));

        Video actualVideo = reactionService.getByVideoId(video.getId());

        assertThat(actualVideo).isNotNull();
        assertEquals(actualVideo,video);
    }

    @Test
    void getByVideoId_shouldThrowExceptionEntityNotFound(){
        Long videoId = 2L;
        assertThatThrownBy(() -> reactionService.getByVideoId(videoId))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getALlReactionsByVideoIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        List<Reaction> reactionList = ReactionMother.createReactionListValid(video);

        given(reactionRepository.findAllByVideoId(video.getId())).willReturn(reactionList);

        List<Reaction> actualReactionList = reactionService.getALlReactionsByVideoId(video.getId());

        assertThat(actualReactionList).isNotNull();
        assertEquals(actualReactionList,reactionList);
    }

    @Test
    void updateReaction() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        Reaction reaction = ReactionMother.createReactionValid(video);
        ReactionDTO reactionDTO = ReactionDTOMother.createReactionDTOValidFrom(reaction);

        given(reactionRepository.findByIdAndVideoId(
                reaction.getId(),
                video.getId()
        )).willReturn(Optional.of(reaction));
        willDoNothing().given(mapper).map(reactionDTO,reaction);
        given(reactionRepository.save(reaction)).willReturn(reaction);

        Reaction actualReaction = reactionService.updateReaction(video.getId(), reaction.getId(),reactionDTO);

        assertThat(actualReaction).isNotNull();
        assertEquals(actualReaction,reaction);

    }

    @Test
    void createReaction() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        Reaction reaction = ReactionMother.createReactionValid(video);
        ReactionDTO reactionDTO = ReactionDTOMother.createReactionDTOValidFrom(reaction);

        given(videoRepository.findById(video.getId())).willReturn(Optional.of(video));
        given(mapper.map(reactionDTO,Reaction.class)).willReturn(reaction);
        given(reactionRepository.save(reaction)).willReturn(reaction);

        Reaction actualReaction = reactionService.createReaction(video.getId(),reactionDTO);

        assertThat(actualReaction).isNotNull();
        assertEquals(actualReaction,reaction);
    }

    @Test
    void deleteById() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        Reaction reaction = ReactionMother.createReactionValid(video);

        given(reactionRepository.findByIdAndVideoId(reaction.getId(), video.getId())).willReturn(Optional.of(reaction));
        willDoNothing().given(reactionRepository).delete(reaction);

        reactionService.deleteById(video.getId(), reaction.getId());

        verify(reactionRepository).delete(reaction);
    }
}