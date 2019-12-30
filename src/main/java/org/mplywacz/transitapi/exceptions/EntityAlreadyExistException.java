package org.mplywacz.transitapi.exceptions;
/*
Author: BeGieU
Date: 27.11.2019
*/

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException(String msg) {
        super(msg);
    }

    public EntityAlreadyExistException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
