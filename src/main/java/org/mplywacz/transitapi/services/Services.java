package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 03.12.2019
*/

import org.mplywacz.transitapi.exceptions.UnprocessableRequestException;

public abstract class Services {
    public static void containsOnlyLetter(String str,String exceptionMsg) {
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                throw new UnprocessableRequestException(exceptionMsg);
            }
        }

    }
}
