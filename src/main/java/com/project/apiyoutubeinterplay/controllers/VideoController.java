package com.project.apiyoutubeinterplay.controllers;

import com.project.apiyoutubeinterplay.dtos.VideoDTO;
import com.project.apiyoutubeinterplay.models.Video;
import com.project.apiyoutubeinterplay.services.impl.VideoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel/{channelId}/video")
public class VideoController {
    private final VideoServiceImpl videoService;

    public VideoController(VideoServiceImpl videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos(
            @PathVariable @Valid Long channelId
    ){
        List<Video> videos = videoService.getAllVideosByChannelId(channelId);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideo(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long videoId
    ){
        Video video = videoService.getByVideoIdAndChannelId(videoId,channelId);
        return ResponseEntity.ok(video);
    }

    @PostMapping
    public ResponseEntity<Video> createVideo(
            @PathVariable @Valid Long channelId,
            @RequestBody @Valid VideoDTO videoDTO
    ){
        Video video = videoService.createVideo(channelId,videoDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(video);
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long videoId,
            @RequestBody @Valid VideoDTO videoDTO
    ){
        Video video = videoService.updateVideo(channelId,videoId,videoDTO);
        return ResponseEntity.ok(video);
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long videoId
    ){
        videoService.deleteById(channelId,videoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
