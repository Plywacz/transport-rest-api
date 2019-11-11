package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONException;
import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.dto.DriverReport;
import org.mplywacz.demo.model.Driver;
import org.mplywacz.demo.services.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Api(value = "Driver Management System",description = "Operations pertaining to driver in Driver Management System")
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @ApiOperation(value = "Add new driver to the system", response = Driver.class)
    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Driver addDriver(
            @ApiParam(value = "JSON object that represent new driver", required = true)
            @Valid
            @RequestBody
                    DriverDto driverDto) {
        return driverService.addDriver(driverDto);
    }

    @ApiOperation(value = "Update driver that already exist in system", response = Driver.class)
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Driver updateDriver(
            @ApiParam(value = "JSON object that that contains new data to be updated", required = true)
            @Valid
            @RequestBody
                    DriverDto driverDto,
            @ApiParam(value = "Id of driver to be updated")
            @PathVariable
            @Min(1)
                    Long id) {
        return driverService.updateDriver(driverDto, id);
    }

    @ApiOperation(value = "View already existing driver from system",response = Driver.class)
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Driver getDriver(
            @ApiParam(value = "Id of driver to be viewed", required = true)
            @PathVariable
            @Min(1)
                    Long id) {
        return driverService.getDriver(id);
    }


    @ApiOperation(value = "Delete already existing driver from system",response = String.class)
    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteDriver(
            @ApiParam("Id of driver to be deleted")
            @PathVariable
            @Min(1)
                    Long id) throws JSONException {
        driverService.deleteDriver(id);
    }

    @ApiOperation(value = "View report of driver",response = DriverReport.class)
    @GetMapping(value = "/reports/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody DriverReport getDriverReport(
            @ApiParam("Id of driver whose report is to be shown")
            @PathVariable
            @Min(1)
                    Long id) {
        return driverService.getDriverReport(id);

    }
}
