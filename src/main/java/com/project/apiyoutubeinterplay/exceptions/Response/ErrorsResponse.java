package com.project.apiyoutubeinterplay.exceptions.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ErrorsResponse {
    private HttpStatus errorCode;
    private String message;
    private List<FieldError> details;
}
