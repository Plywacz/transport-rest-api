package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 02.07.2019
*/


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.mplywacz.demo.dto.DailyInfo;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.services.TransitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(value = "Transit Management System", description = "Operations pertaining to transits in Transit Management System")
@RestController
@RequestMapping("/api/transits")
public class TransitController {
    private final TransitService transitService;

    public TransitController(TransitService transitService) {
        this.transitService = transitService;
    }

    @ApiOperation(value = "Add transit to the system", response = Transit.class)
    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Transit addTransit(
            @ApiParam(value = "object that represents transit", required = true)
            @Valid
            @RequestBody
                    TransitDto transitDto) {
        return transitService.addTransit(transitDto);
    }


    //get report that contains stats about whole transits that took place between given dates
    @ApiOperation(value = "View report from given period",
            response = String.class)
    @GetMapping(value = "/reports/range/{date1}&&{date2}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody String getRangeReport(
            @ApiParam(value = "date where report begins", required = true,example = "2001-12-01")
            @PathVariable
            @NotEmpty(message = "date where report ends")
                    String date1,
            @ApiParam(value = "object that represents period of time", required = true,example = "2012-02-20")
            @PathVariable
            @NotEmpty(message = "end date cannot be null")
                    String date2) {
        return transitService.getRangeReport(date1,date2).toString();

    }

    @ApiOperation(value = "View operations that took place in current month to this day",
            response = DailyInfo.class,
            responseContainer = "List")
    @GetMapping(value = "/reports/monthly",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DailyInfo> getMonthlyReport() {
        return transitService.getMonthlyReport();
    }
}
