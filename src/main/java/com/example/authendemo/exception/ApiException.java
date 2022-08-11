package com.example.authendemo.exception;

import com.example.authendemo.dto.response.ApiResponse;
import com.example.authendemo.dto.response.Msg;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class ApiException {

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiResponse> handlerForbidden(ForbiddenException ex) {
        Msg msg = Msg.builder()
                .message(ex.getMessage())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse apiResponse = ApiResponse.builder().msg(msg).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handlerBadRequest(BadRequestException ex) {
        Msg msg = Msg.builder()
                .message(ex.getMessage())
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse apiResponse = ApiResponse.builder().msg(msg).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<ApiResponse> handlerNotFound(NotFoundException ex) {
        Msg msg = Msg.builder()
                .message(ex.getMessage())
                .status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .timestamp(String.valueOf(LocalDateTime.now()))
                .build();
        ApiResponse apiResponse = ApiResponse.builder().msg(msg).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

}
