package org.mplywacz.demo.dto.mappers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.model.Driver;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DriverMapperImpl implements Mapper<DriverDto, Driver> {
    @Override public Driver convertDto(DriverDto driverDto) {
        return new Driver(driverDto.getFirstName(),
                driverDto.getLastName(),
                LocalDate.now());
    }
}
