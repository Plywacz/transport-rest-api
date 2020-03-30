package org.mplywacz.transitapi.services;

import org.mplywacz.transitapi.dto.*;
import org.mplywacz.transitapi.dto.mappers.Mapper;
import org.mplywacz.transitapi.exceptions.EntityAlreadyExistException;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.repositories.DriverRepo;
import org.springframework.stereotype.Service;

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
        containsOnlyLetter(driverDto.getFirstName(), "first name must contain only letters");
        containsOnlyLetter(driverDto.getFirstName(), "last name must contain only letters");
        isAlreadyInDb(driverDto);
        return driverRepo.save(driverMapper.convertDto(driverDto));
    }

    private void isAlreadyInDb(DriverDto driverDto) {
        if (
                driverRepo.findDriverByFirstNameAndLastName(
                        driverDto.getFirstName(),
                        driverDto.getLastName()) != null) {
            throw new EntityAlreadyExistException("driver with given first name and alst name already exist in db");
        }
    }

    @Override public Driver getDriver(Long id) {
        return driverRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("Driver with given id: " + id + " not found"));
    }

    @Override public String deleteDriver(Long id) {
        if (!driverRepo.existsById(id))
            throw new NoSuchElementException("Driver with given id: " + id + " not found.");
        driverRepo.deleteById(id);

        return "deleted driver with id: " + id;
    }

    @Override
    public DriverReport getDriverReport(Long id) {
        if (!driverRepo.existsById(id))
            throw new NoSuchElementException("Wanted driver doesnt exist in DB ");

        var basicDriverInfo = driverRepo.getBasicDriverInfo(id);
        var monthlyDriverInfo = driverRepo.getMonthlyDriverInfo(id);
        var totalDriverInfo = driverRepo.getTotalDriverInfo(id);

        return createDriverReport(basicDriverInfo, totalDriverInfo, monthlyDriverInfo);
    }

    private DriverReport createDriverReport(BasicDriverInfo basicDriverInfo,
                                            TotalDriverInfo totalDriverInfo,
                                            List<MonthlyDriverInfo> monthlyDriverInfo) {
        return new DriverReport(
                basicDriverInfo,
                totalDriverInfo,
                monthlyDriverInfo);
    }

    @Override
    public Driver updateDriver(DriverDto driverDto, Long id) {
        driverRepo
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Driver with given id: " + id + " doesnt exist"));

        isAlreadyInDb(driverDto);
        containsOnlyLetter(driverDto.getFirstName(), "illegal first name given");
        containsOnlyLetter(driverDto.getFirstName(), "illegal last name given");

        var updatedDriver = driverMapper.convertDto(driverDto);
        updatedDriver.setId(id);
        return driverRepo.save(updatedDriver);
    }


}
