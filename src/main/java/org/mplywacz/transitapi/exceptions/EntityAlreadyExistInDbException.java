package org.mplywacz.transitapi.exceptions;
/*
Author: BeGieU
Date: 27.11.2019
*/

public class EntityAlreadyExistInDbException extends RuntimeException {
    public EntityAlreadyExistInDbException(String msg) {
        super(msg);
    }

    public EntityAlreadyExistInDbException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
