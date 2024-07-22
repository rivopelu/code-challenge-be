package com.codechallege.rivo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SystemErrorException extends RuntimeException {
    public SystemErrorException(String e) {
        super(e != null ? e : "Internal Server Error");
    }

}
