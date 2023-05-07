package com.project.apiyoutubeinterplay.dtos;

import com.project.apiyoutubeinterplay.validators.annotation.NotWhitespace;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkDTO {

    @NotBlank(message = "El name del canal es obligatorio")
    @NotWhitespace
    private String name;

    @NotBlank(message = "El uri del canal es obligatorio")
    @NotWhitespace
    private String uri;

    @Nonnull
    private Long idChannel;

}
