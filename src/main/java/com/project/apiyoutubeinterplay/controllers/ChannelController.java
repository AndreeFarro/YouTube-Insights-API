package com.project.apiyoutubeinterplay.controllers;

import com.project.apiyoutubeinterplay.dtos.ChannelDTO;
import com.project.apiyoutubeinterplay.models.Channel;
import com.project.apiyoutubeinterplay.services.impl.ChannelServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("channel")
public class ChannelController {

    private final ChannelServiceImpl channelService;

    public ChannelController(ChannelServiceImpl channelService) {
        this.channelService = channelService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Channel> getChannel(
            @Valid @PathVariable Long id
    ) {
        Channel channel = channelService.getChannelById(id);
        return ResponseEntity.ok(channel);
    }

    @GetMapping
    public ResponseEntity<List<Channel>> getAllChannels() {
        List<Channel> channels = channelService.getAll();
        return ResponseEntity.ok(channels);
    }

    @PostMapping
    public ResponseEntity<Channel> createChannel(
            @Valid @RequestBody ChannelDTO channelDTO
    ) {
        Channel channel = channelService.createChannel(channelDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(channel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteChannel(
            @Valid @PathVariable Long id
    ) {
        channelService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Channel> updateChannel(
            @PathVariable @Valid Long id,
            @RequestBody @Valid ChannelDTO channelDTO
    ) {
        Channel channel = channelService.updateChannel(id, channelDTO);
        return ResponseEntity.ok(channel);
    }


}
