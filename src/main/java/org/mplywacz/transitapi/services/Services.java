package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 03.12.2019
*/

public abstract class Services {
    public static void containsOnlyLetter(String str,String exceptionMsg) {
        char[] chars = str.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                throw new IllegalArgumentException(exceptionMsg);
            }
        }

    }
}
