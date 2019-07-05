package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;

import java.math.BigDecimal;
import java.sql.Date;

public class TransitService {

    public void addTransit(final TransitDto transitDto){
        var transit=new Transit();
        transit.setSourceAddress(transitDto.getSourceAddress());
        transit.setDestinationAddress(transitDto.getDestinationAddress());
        transit.setDate(Date.valueOf(transitDto.getDate())); // localdate -> sqldate
        transit.setPrice(transitDto.getPrice());
        
        transit.setDistance(calculateDistance());

    }

    //private static final BigDecimal sd = new BigDecimal(0.7d, MathContext.DECIMAL64)

    private BigDecimal calculateDistance() {
        return null;
    }


}
