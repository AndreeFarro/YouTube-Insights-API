package com.project.apiyoutubeinterplay.services;

import com.project.apiyoutubeinterplay.dtos.ReactionDTO;
import com.project.apiyoutubeinterplay.models.Reaction;

import java.util.List;

public interface ReactionService {
    Reaction getReactionById(Long videoId, Long reactionId);

    void deleteById(Long videoId, Long reactionId);

    List<Reaction> getALlReactionsByVideoId(Long videoId);

    Reaction updateReaction(Long videoId, Long reactionId, ReactionDTO reactionDto);

    Reaction createReaction(Long videoId, ReactionDTO reactionDto);
}
