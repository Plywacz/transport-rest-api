package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.dto.DriverReport;
import org.mplywacz.transitapi.model.Driver;

public interface DriverService {
    Iterable<Driver> getAllDrivers();

    Driver addDriver(DriverDto driverDto);

    Driver getDriver(Long id);

    String deleteDriver(Long id);

    DriverReport getDriverReport(Long id);

    Driver updateDriver(DriverDto driverDto, Long id);
}
