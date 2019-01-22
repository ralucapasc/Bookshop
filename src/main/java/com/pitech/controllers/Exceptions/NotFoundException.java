package com.pitech.controllers.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Order")
public class NotFoundException extends Exception {

    public NotFoundException(String error) {

        super(error);
    }
}
