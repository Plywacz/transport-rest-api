package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import org.json.JSONException;
import org.json.JSONObject;
import org.mplywacz.transitapi.dto.DailyInfo;
import org.mplywacz.transitapi.dto.TransitDto;
import org.mplywacz.transitapi.dto.mappers.Mapper;
import org.mplywacz.transitapi.exceptions.EntityNotFoundException;
import org.mplywacz.transitapi.model.Transit;
import org.mplywacz.transitapi.repositories.DriverRepo;
import org.mplywacz.transitapi.repositories.TransitRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
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
        var driverId = transitDto.getDriverId();
        var driverOpt = driverRepo.findById(driverId);
        var driver = driverOpt.orElseThrow(
                () -> new EntityNotFoundException("couldn't add transit to db because driver with ID: "
                        + driverId + " related with this transit doesn't exist in db. ")
        );

        var incompleteTransit = transitMapper.convertDto(transitDto);
        incompleteTransit.setDriver(driver);

        try {
            incompleteTransit.setDistance(
                    distanceCalculator.calculateDistance(
                            transitDto.getSourceAddress(),
                            transitDto.getDestinationAddress()
                    )
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            distanceSum = distanceSum.add(t.getDistance());
            priceSum = priceSum.add(t.getPrice());
        }

        try {
            return new JSONObject()
                    .put("total_distance", distanceSum.doubleValue())
                    .put("total_price", priceSum.doubleValue());
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

    @Override
    public String deleteTransit(Long id) {
        if (!transitRepository.existsById(id)) {
            throw new NoSuchElementException("Transit with id: " + id + " doesn't exist");
        }
        transitRepository.deleteById(id);
        return "Successfully deleted transit with id: " + id;
    }
}
