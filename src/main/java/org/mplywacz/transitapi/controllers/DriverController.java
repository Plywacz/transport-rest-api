package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.dto.DriverReport;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.services.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    //todo Add this endpoint to swagger doc !!!
    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Driver addDriver(
            @Valid
            @RequestBody
                    DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }


    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Driver updateDriver(
            @Valid
            @RequestBody
                    DriverDto driverDto,
            @PathVariable
            @Min(0)
                    Long id) {
        return driverService.updateDriver(driverDto, id);
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Driver getDriver(
            @PathVariable
            @Min(0)
                    Long id) {
        return driverService.getDriver(id);
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String deleteDriver(
            @PathVariable
            @Min(0)
                    Long id) {
        return driverService.deleteDriver(id);
    }

    @GetMapping(value = "/{id}/report",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody DriverReport getDriverReport(
            @PathVariable
            @Min(0)
                    Long id) {
        return driverService.getDriverReport(id);

    }
}
