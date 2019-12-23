package org.mplywacz.transitapi.services;

import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.dto.DriverReport;
import org.mplywacz.transitapi.dto.mappers.Mapper;
import org.mplywacz.transitapi.exceptions.EntityAlreadyExistInDbException;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.repositories.DriverRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mplywacz.transitapi.services.Services.containsOnlyLetter;

/*
Author: BeGieU
Date: 08.10.2019
*/
@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepo driverRepo;
    private final Mapper<DriverDto, Driver> driverMapper;

    public DriverServiceImpl(DriverRepo driverRepo, Mapper<DriverDto, Driver> driverMapper) {
        this.driverRepo = driverRepo;
        this.driverMapper = driverMapper;
    }

    @Override public Driver addDriver(DriverDto driverDto) {
        containsOnlyLetter(driverDto.getFirstName(), "illegal first name given");
        containsOnlyLetter(driverDto.getLastName(), "illegal last name given");
        isAlreadyInDb(driverDto);
        return driverRepo.save(driverMapper.convertDto(driverDto));
    }

    private void isAlreadyInDb(DriverDto driverDto) {
        if (
                driverRepo.findDriverByFirstNameAndLastName(
                        driverDto.getFirstName(),
                        driverDto.getLastName()) != null) {
            throw new EntityAlreadyExistInDbException("driver with given first name and alst name already exist in db");
        }
    }

    @Override public Driver getDriver(Long id) {
        return driverRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("Driver with given id: " + id + " not found"));
    }

    @Override public String deleteDriver(Long id) {
        try {
            driverRepo.deleteById(id);
        }
        catch (DataAccessException e) {
            throw new NoSuchElementException("Driver with given id: " + id + " not found, so we couldn't delete");
        }
        return "deleted driver with id: " + id;
    }

    @Override
    public DriverReport getDriverReport(Long id) {
        List<Object[]> totalDriverInfo = driverRepo.getTotalReportForDriver(id);

        var driverId = totalDriverInfo.get(0)[0];
        if (driverId == null) {
            throw new NoSuchElementException(
                    "Wanted driver doesnt exist in DB ");//todo throw appropriate exception
        }

        var driverReport = createTotalDriverReport(totalDriverInfo);

        List<Object[]> monthlyDriverInfo = driverRepo.getReportPerMonthForDriver(id);
        return addMonthlyInfoToDriverReport(driverReport, monthlyDriverInfo);
    }

    private DriverReport addMonthlyInfoToDriverReport(DriverReport driverReport, List<Object[]> monthlyDriverInfo) {
        for (Object[] monthInfo : monthlyDriverInfo) {
            //order of objects in monthInfo[] as in query (check getReportPerMonthForDriver method in DriverRepo)
            var formattedDate = monthInfo[0];
            var totalMonthlyDistance = monthInfo[1];
            var maxMonthlyDistance = monthInfo[2];
            var maxMonthlyTransitPrice = monthInfo[3];
            driverReport.addMonthlyInfo(
                    (String) formattedDate,
                    (BigDecimal) totalMonthlyDistance,
                    (BigDecimal) maxMonthlyDistance,
                    (BigDecimal) maxMonthlyTransitPrice);
        }
        return driverReport;
    }

    private DriverReport createTotalDriverReport(List<Object[]> totalDriverInfo) {
        //total driverRepo.getTotalReportForDriver(id) always returns list that contains ONE Object[]
        // because of query. Arr contains elements related with query in order as in the query
        var driverFirstName = totalDriverInfo.get(0)[1];
        var driverLastName = totalDriverInfo.get(0)[2];
        var totalDistance = totalDriverInfo.get(0)[3];
        var longestTransit = totalDriverInfo.get(0)[4];
        var mostExpensiveTransit = totalDriverInfo.get(0)[5];

        var driverReport = new DriverReport(
                (String) driverFirstName,
                (String) driverLastName);

        driverReport.setTotalInfo(
                (BigDecimal) totalDistance,
                (BigDecimal) longestTransit,
                (BigDecimal) mostExpensiveTransit);

        return driverReport;
    }

    @Override public Driver updateDriver(DriverDto driverDto, Long id) {
        isAlreadyInDb(driverDto);
        containsOnlyLetter(driverDto.getFirstName(), "illegal first name given");
        containsOnlyLetter(driverDto.getFirstName(), "illegal last name given");
        driverRepo
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Driver with given id: " + id + " doesnt exist"));

        var updatedDriver = driverMapper.convertDto(driverDto);
        updatedDriver.setId(id);
        return driverRepo.save(updatedDriver);
    }


}
