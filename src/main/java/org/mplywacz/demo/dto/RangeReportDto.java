package org.mplywacz.demo.dto;
/*
Author: BeGieU
Date: 10.07.2019
*/

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.mplywacz.demo.json.RangeReportDtoDeserialization;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@JsonDeserialize(using = RangeReportDtoDeserialization.class)
public class RangeReportDto {

    @NotNull(message = "start date cannot be null")
    private Date startDate;

    @NotNull(message = "start date cannot be null")
    private Date endDate;

    public RangeReportDto() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
