package com.project.apiyoutubeinterplay.services.impl;

import com.project.apiyoutubeinterplay.dtos.ReactionDTO;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.repositories.ReactionRepository;
import com.project.apiyoutubeinterplay.repositories.VideoRepository;
import com.project.apiyoutubeinterplay.services.ReactionService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final VideoRepository videoRepository;
    private final ModelMapper mapper;

    public ReactionServiceImpl(ReactionRepository reactionRepository, VideoRepository videoRepository, ModelMapper mapper) {
        this.reactionRepository = reactionRepository;
        this.videoRepository = videoRepository;
        this.mapper = mapper;
    }

    @Override
    public Reaction getReactionById( Long videoId, Long reactionId){
        return reactionRepository
                .findByIdAndVideoId(reactionId,videoId)
                .orElseThrow(()->new EntityNotFoundException(
                        "Reaction not Found with video.id: " + videoId +
                                " and reaction.id: " + reactionId
                ));
    }

    public Video getByVideoId(Long videoId){
        return videoRepository
                .findById(videoId)
                .orElseThrow(()->new EntityNotFoundException(
                        "Reaction not Found with video.id: " + videoId
                ));
    }

    @Override
    public List<Reaction> getALlReactionsByVideoId(Long videoId){
        return reactionRepository.findAllByVideoId(videoId);
    }

    @Override
    public Reaction updateReaction(Long videoId, Long reactionId, ReactionDTO reactionDto){
        Reaction reaction = getReactionById(videoId,reactionId);
        mapper.map(reactionDto, reaction);

        return reactionRepository.save(reaction);
    }

    @Override
    public Reaction createReaction(Long videoId, ReactionDTO reactionDto){
        Video existingVideo = getByVideoId(videoId);
        Reaction reaction = mapper.map(reactionDto, Reaction.class);
        reaction.setVideo(existingVideo);

        return reactionRepository.save(reaction);
    }

    @Override
    public void deleteById(Long videoId, Long reactionId){
        Reaction existingReaction = getReactionById(videoId,reactionId);
        reactionRepository.delete(existingReaction);
    }
}
