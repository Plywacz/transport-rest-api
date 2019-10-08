package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 10.07.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.RangeReportDtoDeserializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static org.mplywacz.demo.dto.Dto.buildDate;

@JsonDeserialize(using = RangeReportDtoDeserializer.class)
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

    public void setStartDate(JsonNode startDate) {
        this.startDate = buildDate(startDate);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(JsonNode endDate) {
        this.endDate = buildDate(endDate);
    }


}
