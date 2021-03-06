package org.mplywacz.transitapi.dto.mappers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.model.Driver;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DriverMapperImpl implements Mapper<DriverDto, Driver> {
    @Override
    public Driver convertDto(DriverDto driverDto) {
        /**
         * when driver is being added to DB it means he is now enrolled
         * that is why we create new Driver with actual date
         */
        return new Driver(driverDto.getFirstName(),
                driverDto.getLastName(),
                LocalDate.now());
    }
}
