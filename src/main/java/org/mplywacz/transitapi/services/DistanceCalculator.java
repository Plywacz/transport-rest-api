package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 27.09.2019
*/

import java.io.IOException;
import java.math.BigDecimal;

public interface DistanceCalculator {
    BigDecimal calculateDistance(String sAddr, String eAddr) throws IOException;
}
