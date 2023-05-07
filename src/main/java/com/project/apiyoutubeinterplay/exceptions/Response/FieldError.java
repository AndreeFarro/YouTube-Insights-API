package com.project.apiyoutubeinterplay.exceptions.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldError {
    private String field;
    private String message;
}
