package org.mplywacz.transitapi.exceptions;
/*
Author: BeGieU
Date: 23.12.2019
*/

public class UnprocessableRequestException  extends RuntimeException{
    public UnprocessableRequestException(String msg) {
        super(msg);
    }

    public UnprocessableRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
