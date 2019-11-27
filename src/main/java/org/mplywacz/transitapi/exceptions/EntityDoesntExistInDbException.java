package org.mplywacz.transitapi.exceptions;
/*
Author: BeGieU
Date: 27.11.2019
*/

public class EntityDoesntExistInDbException extends RuntimeException{
    public EntityDoesntExistInDbException(String msg) {
        super(msg);
    }

    public EntityDoesntExistInDbException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
