package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.model.Driver;
import org.mplywacz.demo.services.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping(value = "/drivers/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Driver addDriver(@Valid @RequestBody DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }

    @GetMapping(value = "/drivers/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Driver getDriver(@Valid @PathVariable String id) {
        return driverService.getDriver(id);
    }


    @DeleteMapping(value = "/drivers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String deleteDriver(@PathVariable String id) {
        return driverService.deleteDriver(id);
    }
}
