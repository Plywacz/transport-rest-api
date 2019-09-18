package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 10.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.RangeReportDtoDeserialization;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@JsonDeserialize(using = RangeReportDtoDeserialization.class)
public class RangeReportDto {

    @NotNull(message = "start date cannot be null")
    private LocalDate startDate;

    @NotNull(message = "start date cannot be null")
    private LocalDate endDate;

    public RangeReportDto() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
