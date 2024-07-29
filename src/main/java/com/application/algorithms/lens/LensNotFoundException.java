package com.application.algorithms.lens;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LensNotFoundException extends RuntimeException{
        public LensNotFoundException(){
            super("Lens Not Found");
        }
}
    