package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONException;
import org.json.JSONObject;
import org.mplywacz.demo.dto.DailyInfo;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.dto.mappers.Mapper;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.DriverRepo;
import org.mplywacz.demo.repositories.TransitRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

@Service
public class TransitServiceImpl implements TransitService {
    private final TransitRepo transitRepository;
    private final DriverRepo driverRepo;
    private final Mapper<TransitDto, Transit> transitMapper;
    private final DistanceCalculator distanceCalculator;

    public TransitServiceImpl(TransitRepo transitRepository,
                              DriverRepo driverRepo,
                              Mapper<TransitDto, Transit> transitMapper,
                              DistanceCalculator distanceCalculator) {
        this.transitRepository = transitRepository;
        this.driverRepo = driverRepo;
        this.transitMapper = transitMapper;
        this.distanceCalculator = distanceCalculator;
    }

    //todo fix bug: app adds to DB transit which date  is incorrect i.e you add transit with 2019-09-01 date it saves 2019-08-31 !!!!!!!!!
    public Transit addTransit(final TransitDto transitDto) {
        var incompleteTransit = transitMapper.convertDto(transitDto);

        try {
            incompleteTransit.setDistance(distanceCalculator.calculateDistance(
                    transitDto.getSourceAddress(),
                    transitDto.getDestinationAddress()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        var driverId = transitDto.getDriverId();
        var driverOpt = driverRepo.findById(driverId);
        if (driverOpt.isEmpty()) {
            throw new IllegalArgumentException("couldn't add transit to db because driver with ID: "
                    + driverId + " related with this transit doesn't exist in db. ");
        }

        var driver = driverOpt.get();
        incompleteTransit.setDriver(driver);
        //after this line incomplete transit is already complete
        driver.addTransit(incompleteTransit);

        return transitRepository.save(incompleteTransit);
    }

    @Override
    public JSONObject getRangeReport(String startDate, String endDate) {
        LocalDate sDate = getLocalDateFromString(startDate);
        LocalDate eDate = getLocalDateFromString(endDate);

        //no need to check if sDate or eDate is null 'cause it is already done in deserializer
        if (sDate.compareTo(eDate) > 0) {
            throw new IllegalArgumentException("you  provided wrong start and end date");
        }

        Set<Transit> transits = transitRepository
                .selectTransitsInDateRange(sDate, eDate);

        BigDecimal distanceSum = new BigDecimal(0);
        BigDecimal priceSum = new BigDecimal(0);

        for (Transit t : transits) {
            var distance = t.getDistance();
            var price = t.getPrice();
            if (distance.compareTo(BigDecimal.ZERO) < 0 ||
                    price.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Records fetched from DB are corrupted by STH !!!");
            }

            distanceSum = distanceSum.add(distance);
            priceSum = priceSum.add(price);
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

    private LocalDate getLocalDateFromString(String date) {
        try {
            return LocalDate.parse(date);
        }
        catch (DateTimeParseException e) {
            CharSequence cs = date;
            throw new DateTimeParseException("provide date in correct format i.e: yyyy-mm-dd", cs, 400);
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
