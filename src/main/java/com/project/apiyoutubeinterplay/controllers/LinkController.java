package com.project.apiyoutubeinterplay.controllers;

import com.project.apiyoutubeinterplay.dtos.LinkDTO;
import com.project.apiyoutubeinterplay.models.Link;
import com.project.apiyoutubeinterplay.services.impl.LinkServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/channel/{channelId}/link")
public class LinkController {
    private final LinkServiceImpl linkService;

    public LinkController(LinkServiceImpl linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public ResponseEntity<List<Link>> getAllLinks(
            @PathVariable @Valid Long channelId
    ){
        List<Link> links = linkService.getALlLinksByChannelId(channelId);
        return ResponseEntity.ok(links);
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<Link> getLink(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long linkId
    ){
        Link link = linkService.getLinkByIdAndChannelId(linkId, channelId);
        return ResponseEntity.ok(link);
    }

    @PostMapping
    public ResponseEntity<Link> createLink(
            @PathVariable @Valid Long channelId,
            @RequestBody @Valid LinkDTO linkDTO
    ){
        Link savedLink = linkService.createLink(channelId,linkDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedLink);
    }

    @PutMapping("/{linkId}")
    public ResponseEntity<Link> updateLink(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long linkId,
            @RequestBody @Valid LinkDTO linkDTO
    ){
        Link updatedLink = linkService.updateLink(channelId,linkId,linkDTO);

        return ResponseEntity.ok(updatedLink);
    }

    @DeleteMapping("{linkId}")
    public ResponseEntity<Void> deleteLink(
            @PathVariable @Valid Long channelId,
            @PathVariable @Valid Long linkId
    ){
        linkService.deleteById(channelId,linkId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }


}
