package org.mplywacz.demo.dto.mappers;
/*
Author: BeGieU
Date: 16.09.2019
*/

import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.springframework.stereotype.Component;

@Component
public class TransitMapperImpl implements Mapper<TransitDto, Transit> {
    public TransitMapperImpl() {
    }

    /**
     * @param dto to be mapped on model object
     * @return unfinished model object which contains only fields that assignment to them requires no logic.
     * basic
     */
    public Transit convertDto(TransitDto dto) {
        var transit = new Transit();
        transit.setSourceAddress(dto.getSourceAddress());
        transit.setDestinationAddress(dto.getDestinationAddress());
        transit.setDate(dto.getDate());
        transit.setPrice(dto.getPrice());

        return transit;
    }
}
