package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.dto.DriverReport;
import org.mplywacz.demo.model.Driver;
import org.mplywacz.demo.services.DriverService;
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

    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Driver addDriver(@Valid @RequestBody DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Driver getDriver(@PathVariable @Min(1) Long id) {
        return driverService.getDriver(id);
    }


    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String deleteDriver(@PathVariable @Min(1) Long id) {
        return driverService.deleteDriver(id);
    }

    @GetMapping(value = "/reports/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody DriverReport getDriverReport(@PathVariable @Min(1) Long id){
       return driverService.getDriverReport(id);

    }
}
