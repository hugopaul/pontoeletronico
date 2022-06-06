package br.com.imsodontologia.pontoeletronico.exception;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiError {

    @Getter
    private List<String> errors;

    public ApiError(List<String> errors){
        this.errors = errors;
    }
    public ApiError(String message){
        this.errors = Arrays.asList(message);
    }
}