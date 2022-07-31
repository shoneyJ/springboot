package com.myhotel.reservations.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OutOfRangeException extends  RuntimeException
{
    public OutOfRangeException(String message){
        super(message);
    }
}


