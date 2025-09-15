package com.berozgar.accounts.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s Resource not found for field %s with value %s", resourceName, fieldName, fieldValue));
    }
}
