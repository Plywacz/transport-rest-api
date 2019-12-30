package org.mplywacz.transitapi.exceptions;
/*
Author: BeGieU
Date: 27.11.2019
*/

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
