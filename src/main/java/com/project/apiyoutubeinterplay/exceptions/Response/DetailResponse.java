package com.project.apiyoutubeinterplay.exceptions.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class DetailResponse {
    private HttpStatus errorCode;
    private String message;
}
