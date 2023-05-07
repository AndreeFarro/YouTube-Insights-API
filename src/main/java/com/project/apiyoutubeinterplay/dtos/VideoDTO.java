package com.project.apiyoutubeinterplay.dtos;

import com.project.apiyoutubeinterplay.validators.annotation.NotWhitespace;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoDTO {

    @NotBlank(message = "El name del canal es obligatorio")
    private String name;

    @Nullable
    private String description;

    @NotBlank(message = "El url del canal es obligatorio")
    @NotWhitespace
    private String url;

    @Nonnull
    private Long idChannel;

}
