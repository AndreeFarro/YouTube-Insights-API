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
public class FollowDTO {

    @Nullable
    @Min(value = 0, message = "El valor de 'subs' debe ser mayor o igual a cero")
    private int subs;

    @Nullable
    @Min(value = 0, message = "El valor de 'views' debe ser mayor o igual a cero")
    private int views;

    @Nullable
    @Min(value = 0, message = "El valor de 'videoNumber' debe ser mayor o igual a cero")
    private int videoNumber;

    @Nonnull
    private Long idChannel;

}
