package org.mplywacz.transitapi.exceptions;
/*
Author: BeGieU
Date: 16.09.2019
*/

public class IllegalDateInputException extends IllegalArgumentException {
    public IllegalDateInputException() {
        super();
    }

    public IllegalDateInputException(String msg) {
        super(msg);
    }

    public IllegalDateInputException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
