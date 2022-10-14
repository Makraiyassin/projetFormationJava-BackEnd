package be.digitalcity.projetspringrest.controllers;

import be.digitalcity.projetspringrest.exceptions.ConnectionErrorException;
import be.digitalcity.projetspringrest.exceptions.UnauthorizedException;
import be.digitalcity.projetspringrest.exceptions.UnavailableProductException;
import be.digitalcity.projetspringrest.models.dtos.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        generateErrorResponse(HttpStatus.BAD_REQUEST, ex,
                                errorDTO -> {
                                    for (ObjectError globalError : ex.getGlobalErrors()) {
                                        errorDTO = errorDTO.addInfo("global", globalError.getDefaultMessage());
                                    }
                                    for (FieldError fieldError : ex.getFieldErrors()) {
                                        errorDTO = errorDTO.addInfo(fieldError.getField(), fieldError.getDefaultMessage());
                                    }
                                    return errorDTO;
                                }
                        )
                );
    }

    private ErrorDto generateErrorResponse(HttpStatus status, Exception ex, Function<ErrorDto, ErrorDto> andAdd){
        ErrorDto errorDTO = generateErrorResponse(status, ex);
        return andAdd.apply(errorDTO);
    }

    private ErrorDto generateErrorResponse(HttpStatus status, Exception ex){
        return ErrorDto.builder()
                .message(ex.getMessage())
                .receivedAt( LocalDateTime.now() )
                .status( status.value() )
                .build();
    }
}
