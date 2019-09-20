package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONException;
import org.json.JSONObject;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.dto.mappers.TransitMapper;
import org.mplywacz.demo.model.DailyInfo;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.TransitRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    //todo fix bug: app adds to DB transit which date  is incorrect i.e you add transit with 2019-09-01 date it saves 2019-08-31 !!!!!!!!!
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
    public List<DailyInfo> getMonthlyReport() {
        var dates = getStartAndEndEdgeDates();
        //list of objects that are consistent with query in transitRepo
        return transitRepository.getMonthlyInfoFromDB(dates[0], dates[1]);
    }

    //method generates dates required to fetch transits which took place in current month
    //we need beginEdgeDate which represents first day of current month and
    //endEdgeDate which stands for current day
    private LocalDate[] getStartAndEndEdgeDates() {
        var currDate = LocalDate.now();
        var sDate = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1);

        return new LocalDate[]{sDate, currDate};
    }


}
