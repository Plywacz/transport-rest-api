package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.model.Driver;

public interface DriverService {
    Driver addDriver(DriverDto driverDto);

    Driver getDriver(String id);

    String deleteDriver(String id);
}
