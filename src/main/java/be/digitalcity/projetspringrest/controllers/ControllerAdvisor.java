package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.exceptions.ConnectionErrorException;
import be.digitalcity.projetspringrest.exceptions.UnauthorizedException;
import be.digitalcity.projetspringrest.exceptions.UnavailableProductException;
import be.digitalcity.projetspringrest.models.dtos.ErrorDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ErrorDto> handleException(EntityExistsException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleException(IllegalArgumentException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(UsernameNotFoundException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDto> handleException(UnauthorizedException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
    @ExceptionHandler(UnavailableProductException.class)
    public ResponseEntity<ErrorDto> handleException(UnavailableProductException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
    @ExceptionHandler(ConnectionErrorException.class)
    public ResponseEntity<ErrorDto> handleException(ConnectionErrorException ex, HttpServletRequest req){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorDto.builder()
                                .message(ex.getMessage())
                                .receivedAt( LocalDateTime.now() )
                                .status( HttpStatus.BAD_REQUEST.value() )
                                .method( HttpMethod.resolve(req.getMethod()) )
                                .path( req.getRequestURL().toString() )
                                .build()
                );
    }
}
