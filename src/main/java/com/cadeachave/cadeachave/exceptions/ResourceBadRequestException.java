package com.cadeachave.cadeachave.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException{
    private static final long serialVersionUID=1L;

    public ResourceBadRequestException(String ex){
        super(ex);
    }
}

