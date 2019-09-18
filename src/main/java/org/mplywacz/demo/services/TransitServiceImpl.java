package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONException;
import org.json.JSONObject;
import org.mplywacz.demo.dto.MonthInfoDto;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.dto.mappers.TransitMapper;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.TransitRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Service
public class TransitServiceImpl implements TransitService {
    private final TransitRepo transitRepository;
    private final TransitMapper transitMapper;

    public TransitServiceImpl(TransitRepo transitRepository, TransitMapper transitMapperImpl) {
        this.transitRepository = transitRepository;
        this.transitMapper = transitMapperImpl;
    }

    //todo fix bug: app adds to DB transit which date  is incorrect i.e you add transit with 2019-09-01 date it saves 2019-08-31
    public Transit addTransit(final TransitDto transitDto) {
        if (transitDto == null) {
            throw new IllegalArgumentException("you must provide information about transit");
        }
        return transitRepository.save(transitMapper.convertTransitDto(transitDto));
    }

    @Override
    public JSONObject getRangeReport(RangeReportDto rangeReportDto) {
        if (rangeReportDto == null) {
            throw new IllegalArgumentException("you must provide start and end date");
        }

        Set<Transit> transits = transitRepository
                .selectTransitsInDateRange(rangeReportDto.getStartDate(), rangeReportDto.getEndDate());

        BigDecimal distanceSum = new BigDecimal(0);
        BigDecimal priceSum = new BigDecimal(0);

        for (Transit t : transits) {
            distanceSum = distanceSum.add(t.getDistance());
            priceSum = priceSum.add(t.getPrice());
        }
        //todo check what happens when exception is thrown inside this block
        try {
            return new JSONObject()
                    .put("total_distance", distanceSum.toString())
                    .put("total_price", priceSum.toString());
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MonthInfoDto> getMonthlyReport() {
        var dates = getStartAndEndEdgeDates();
        var transits = transitRepository.selectTransitsInDateRange(dates[0], dates[1]);



        return null;
    }

    //method generates dates required to fetch transits which took place in current month
    //we need beginEdgeDate which represents first day of current month and
    //endEdgeDate which stands for current day
    private Date[] getStartAndEndEdgeDates() {
        var currDate = new java.util.Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd");

        var endEdgeDate = formatter.format(currDate);

        //generate date that represents first day of current month
        var beginEdgeDate = endEdgeDate.substring(0, endEdgeDate.length() - 3) + "-01";

        java.sql.Date sDate = java.sql.Date.valueOf(beginEdgeDate);
        java.sql.Date eDate = java.sql.Date.valueOf(endEdgeDate);

        return new Date[]{sDate, eDate};
    }


}
