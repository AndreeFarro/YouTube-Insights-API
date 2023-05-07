package com.project.apiyoutubeinterplay.dtos;

import com.project.apiyoutubeinterplay.validators.annotation.NotWhitespace;
import com.project.apiyoutubeinterplay.validators.annotation.UniqueHandler;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class ChannelDTO {

    @NotBlank(message = "El nombre del canal es obligatorio")
    private String name;

    @NotBlank(message = "El handler del canal es obligatorio")
    @UniqueHandler
    private String handler;

    @NotBlank(message = "El type  del canal es obligatorio")
    @NotWhitespace
    private String type;

    private String description;
}
