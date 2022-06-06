package br.com.imsodontologia.pontoeletronico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvise {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlerValidationErros(MethodArgumentNotValidException ex){
        BindingResult bindResult = ex.getBindingResult();
        List<String> messages =  bindResult.getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiError(messages);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex){
        String messageError = ex.getReason();
        HttpStatus codigoStatus = ex.getStatus();
        ApiError apiErros = new ApiError(messageError);
        return new ResponseEntity(apiErros, codigoStatus);
    }
}
