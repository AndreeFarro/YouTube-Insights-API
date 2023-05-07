package com.project.apiyoutubeinterplay.controllers;

import com.project.apiyoutubeinterplay.dtos.ReactionDTO;
import com.project.apiyoutubeinterplay.models.Reaction;
import com.project.apiyoutubeinterplay.services.impl.ReactionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("video/{videoId}/reaction")
public class ReactionController {
    private final ReactionServiceImpl reactionService;

    public ReactionController(ReactionServiceImpl reactionService) {
        this.reactionService = reactionService;
    }

    @GetMapping
    public ResponseEntity<List<Reaction>> getAllReactions(
            @PathVariable @Valid Long videoId
    ){
        List<Reaction> reactions = reactionService.getALlReactionsByVideoId(videoId);
        return ResponseEntity.ok(reactions);
    }

    @GetMapping("{reactionId}")
    public ResponseEntity<Reaction> getReaction(
            @PathVariable @Valid Long videoId,
            @PathVariable @Valid Long reactionId
    ){
        Reaction reaction = reactionService.getReactionById(videoId, reactionId);
        return ResponseEntity.ok(reaction);
    }

    @PostMapping
    public ResponseEntity<Reaction> createReaction(
            @PathVariable @Valid Long videoId,
            @RequestBody @Valid ReactionDTO reactionDTO
    ){
        Reaction savedReaction = reactionService.createReaction(videoId,reactionDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReaction);
    }

    @PutMapping("{reactionId}")
    public ResponseEntity<Reaction> updateReaction(
            @PathVariable @Valid Long videoId,
            @PathVariable @Valid Long reactionId,
            @RequestBody @Valid ReactionDTO reactionDTO
    ){
        Reaction updatedReaction = reactionService.updateReaction(videoId,reactionId,reactionDTO);

        return ResponseEntity.ok(updatedReaction);
    }

    @DeleteMapping("{reactionId}")
    public ResponseEntity<Void> deleteReaction(
            @PathVariable @Valid Long videoId,
            @PathVariable @Valid Long reactionId
    ){
        reactionService.deleteById(videoId, reactionId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
