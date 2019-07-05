package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 02.07.2019
*/

import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransitController {

    @PostMapping(value = "/transits/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Transit addTransit(@Valid @RequestBody TransitDto transitDto) {


        return null;
    }
}
