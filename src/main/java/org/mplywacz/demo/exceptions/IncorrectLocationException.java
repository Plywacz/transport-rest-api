package org.mplywacz.demo.exceptions;
/*
Author: BeGieU
Date: 09.07.2019
*/

public class IncorrectLocationException extends RuntimeException {
    public IncorrectLocationException(String message) {
        super(message);
    }

    public IncorrectLocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
