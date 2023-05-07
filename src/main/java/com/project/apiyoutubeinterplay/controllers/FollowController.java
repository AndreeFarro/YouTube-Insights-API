package com.project.apiyoutubeinterplay.controllers;

import com.project.apiyoutubeinterplay.dtos.FollowDTO;
import com.project.apiyoutubeinterplay.models.Follow;
import com.project.apiyoutubeinterplay.services.impl.FollowServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/channel/{channelId}/follow")
public class FollowController {
    private final FollowServiceImpl followService;

    public FollowController(FollowServiceImpl followService) {
        this.followService = followService;
    }

    @GetMapping
    public ResponseEntity<List<Follow>> getAllFollowers(
            @PathVariable @Valid Long channelId
    ) {
        List<Follow> follows = followService.getAllFollowByChannelID(channelId);
        return ResponseEntity.ok(follows);
    }

    @GetMapping("{followId}")
    public ResponseEntity<Follow> getFollow(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long followId
    ) {
        Follow followDto = followService.getFollowByIdAndChannelId(followId, channelId);
        return ResponseEntity.ok(followDto);
    }

    @PostMapping
    public ResponseEntity<Follow> createFollow(
            @PathVariable @Valid Long channelId,
            @RequestBody @Valid FollowDTO followDto
    ) {
        Follow savedFollow = followService.createFollow(channelId, followDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedFollow);
    }

    @PutMapping("/{followId}")
    public ResponseEntity<Follow> updateFollow(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long followId,
            @RequestBody @Valid FollowDTO followDto
    ) {
        Follow updatedFollow = followService.updateFollow(channelId, followId, followDto);

        return ResponseEntity.ok(updatedFollow);
    }

    @DeleteMapping("{followId}")
    public ResponseEntity<Void> deleteFollow(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long followId
    ) {
        followService.deleteById(channelId, followId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
