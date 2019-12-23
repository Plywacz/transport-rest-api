package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 02.07.2019
*/


import org.mplywacz.transitapi.dto.DailyInfo;
import org.mplywacz.transitapi.dto.TransitDto;
import org.mplywacz.transitapi.model.Transit;
import org.mplywacz.transitapi.services.TransitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/api/transits")
public class TransitController {
    private final TransitService transitService;

    public TransitController(TransitService transitService) {
        this.transitService = transitService;
    }

    @PostMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public  Transit addTransit(
            @Valid
            @RequestBody
                    TransitDto transitDto) {
        return transitService.addTransit(transitDto);
    }

    //get report that contains stats about whole transits that took place between given dates
    @GetMapping(value = "/reports/range/{date1}&&{date2}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  String getRangeReport(
            @PathVariable
            @NotEmpty(message = "date where report ends")
                    String date1,
            @PathVariable
            @NotEmpty(message = "end date cannot be null")
                    String date2) {
        return transitService.getRangeReport(date1, date2).toString();
    }

    @GetMapping(value = "/reports/monthly",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DailyInfo> getMonthlyReport() {
        return transitService.getMonthlyReport();
    }
}
