package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONObject;
import org.mplywacz.demo.dto.MonthInfoDto;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;

import java.util.List;

public interface TransitService {
    Transit addTransit(final TransitDto transitDto);

    JSONObject getRangeReport(final RangeReportDto rangeReportDto);

    List<MonthInfoDto> getMonthlyReport();

}
