package com.project.apiyoutubeinterplay.repositories;

import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.mothers.models.ChannelMother;
import com.project.apiyoutubeinterplay.mothers.models.ReactionMother;
import com.project.apiyoutubeinterplay.mothers.models.VideoMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ReactionRepositoryTest {

    @Mock
    private ReactionRepository reactionRepository;

    @Test
    void findByIdAndVideoIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        Reaction reaction = ReactionMother.createReactionValid(video);

        given(reactionRepository.findByIdAndVideoId(
                reaction.getId(),
                video.getId()
        )).willReturn(Optional.of(reaction));

        Reaction expectedReaction = reactionRepository.findByIdAndVideoId(reaction.getId(), video.getId()).get();

        assertNotNull(expectedReaction);
        assertEquals(expectedReaction,reaction);
    }

    @Test
    void findAllByVideoIdAndChannelId() {
        Channel channel = ChannelMother.createChannelValid();
        Video video = VideoMother.createVideoValid(channel);
        List<Reaction> reactions = ReactionMother.createReactionListValid(video);

        given(reactionRepository.findAllByVideoId(video.getId()))
                .willReturn(reactions);

        List<Reaction> expectedReactions = reactionRepository
                .findAllByVideoId(video.getId());

        assertNotNull(expectedReactions);
        assertEquals(expectedReactions,reactions);

    }
}