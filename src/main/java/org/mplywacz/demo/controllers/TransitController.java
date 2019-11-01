package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 02.07.2019
*/

import org.mplywacz.demo.dto.DailyInfo;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.services.TransitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public @ResponseBody Transit addTransit(@Valid @RequestBody TransitDto transitDto) {
        return transitService.addTransit(transitDto);
    }

    //get report that contains stats about whole transits that took place between given dates
    @GetMapping(value = "/reports/range",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody String getRangeReport(@Valid @RequestBody RangeReportDto rangeReportDto) {
        return transitService.getRangeReport(rangeReportDto).toString();

    }

    //returns report that contains stats about transits that took place in current month
    @GetMapping(value = "/reports/monthly",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<DailyInfo> getMonthlyReport() {
        return transitService.getMonthlyReport();
    }
}
