package com.project.apiyoutubeinterplay.dtos;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO {

    @Nullable
    @Min(value = 0, message = "El valor de 'views' debe ser mayor o igual a cero")
    private int views;

    @Nullable
    @Min(value = 0, message = "El valor de 'coments' debe ser mayor o igual a cero")
    private int coments;

    @Nullable
    @Min(value = 0, message = "El valor de 'likes' debe ser mayor o igual a cero")
    private int likes;

    @Nonnull
    private Long idVideo;

}
