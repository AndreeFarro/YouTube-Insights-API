package com.project.apiyoutubeinterplay.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.apiyoutubeinterplay.exceptions.Response.ErrorResponse;
import com.project.apiyoutubeinterplay.exceptions.Response.ErrorsResponse;
import com.project.apiyoutubeinterplay.exceptions.Response.FieldError;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsResponse handlerMethodArgumentNotValidException(@NotNull MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        String message = "Validación fallida";
        return new ErrorsResponse(HttpStatus.BAD_REQUEST, message, fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String message = "El mensaje HTTP no se puede leer correctamente";
        FieldError fieldErrorDTO = null;

        if (ex.getCause() instanceof InvalidFormatException invalidEx) {
            String fieldName = invalidEx.getPath().get(0).getFieldName();
            String invalidValue = invalidEx.getValue().toString();

            String fieldErrorMessage = String.format("El valor '%s' no es válido para el campo '%s'", invalidValue, fieldName);
            fieldErrorDTO = new FieldError(fieldName,fieldErrorMessage);
            return new ErrorResponse(HttpStatus.BAD_REQUEST, message,fieldErrorDTO);
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST, message, fieldErrorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String message = "No hay coincidencia en el tipo de argumento";
        String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        String fieldName =  ex.getName();
        String messageField = String.format("El parámetro %s debe ser un valor válido de tipo %s", ex.getName(), type);

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                new FieldError(fieldName, messageField)
        );

    }
}
