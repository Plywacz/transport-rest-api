package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONObject;
import org.mplywacz.transitapi.dto.DailyInfo;
import org.mplywacz.transitapi.dto.TransitDto;
import org.mplywacz.transitapi.model.Transit;

import java.util.List;

public interface TransitService {
    Transit addTransit(final TransitDto transitDto);

    JSONObject getRangeReport(String startDate, String endDate);

    List<DailyInfo> getMonthlyReport();

    String deleteTransit(Long id);

}
