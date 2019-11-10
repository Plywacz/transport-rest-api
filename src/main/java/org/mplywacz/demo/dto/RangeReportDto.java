package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 10.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import org.mplywacz.demo.json.RangeReportDtoDeserializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonDeserialize(using = RangeReportDtoDeserializer.class)
public class RangeReportDto {

    @ApiModelProperty(
            notes = "date where report begins",
            required = true,
            position = 0,
            example = "2019-01-12")
    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;

    @ApiModelProperty(
            notes = "date where report ends",
            required = true,
            position = 1,
            example = "2019-03-22")
    @NotNull(message = "start date cannot be null")
    private LocalDate endDate;

    public RangeReportDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }

}
