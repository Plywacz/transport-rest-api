package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 08.10.2019
*/

import io.swagger.annotations.*;
import org.json.JSONException;
import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.dto.DriverReport;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.services.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Successfully added driver to the system"),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 403, message = "access to resource is forbidden- access denied"),
            @ApiResponse(code = 404, message = "resource not fund"),
            @ApiResponse(code = 422, message = "The resource you were trying to add is not suitable")
    })
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
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully updated driver "),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 403, message = "access to resource is forbidden- access denied"),
            @ApiResponse(code = 404, message = "resource not found"),
            @ApiResponse(code = 422, message = "The resource you you sent to update is not suitable")
    })
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
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully viewed driver "),
            @ApiResponse(code = 401, message = "You are not authorized to get the resource"),
            @ApiResponse(code = 403, message = "access to resource is forbidden- access denied"),
            @ApiResponse(code = 404, message = "resource not found")
    })
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
    @ApiResponses( value = {
            @ApiResponse(code = 204, message = "Successfully deleted driver "),
            @ApiResponse(code = 401, message = "You are not authorized to add the resource"),
            @ApiResponse(code = 403, message = "access to resource is forbidden- access denied"),
            @ApiResponse(code = 404, message = "resource not found"),
    })
    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteDriver(
            @ApiParam("Id of driver to be deleted")
            @PathVariable
            @Min(1)
                    Long id) throws JSONException {
        return ResponseEntity.ok(driverService.deleteDriver(id));
    }

    @ApiOperation(value = "View report of driver",response = DriverReport.class)
    @GetMapping(value = "/{id}/report",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully viewed driver report "),
            @ApiResponse(code = 401, message = "You are not authorized to get the resource"),
            @ApiResponse(code = 403, message = "access to resource is forbidden- access denied"),
            @ApiResponse(code = 404, message = "resource not found")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody DriverReport getDriverReport(
            @ApiParam("Id of driver whose report is to be shown")
            @PathVariable
            @Min(1)
                    Long id) {
        return driverService.getDriverReport(id);

    }
}
