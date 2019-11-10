package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 10.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.RangeReportDtoDeserializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonDeserialize(using = RangeReportDtoDeserializer.class)
public class RangeReportDto {

    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;

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
