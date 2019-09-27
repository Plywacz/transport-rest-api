package org.mplywacz.demo.dto.mappers;
/*
Author: BeGieU
Date: 16.09.2019
*/

import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.services.DistanceCalculator;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TransitMapperImpl implements TransitMapper {

    private final DistanceCalculator distanceCalculator;

    public TransitMapperImpl(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    public Transit convertTransitDto(TransitDto dto) {
        var transit = new Transit();
        transit.setSourceAddress(dto.getSourceAddress());
        transit.setDestinationAddress(dto.getDestinationAddress());
        transit.setDate(dto.getDate());
        transit.setPrice(dto.getPrice());

        //handling checked exception, converting them to unchecked
        try {
            transit.setDistance(distanceCalculator.calculateDistance(
                    dto.getSourceAddress(), dto.getDestinationAddress()));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return transit;
    }
}
