package org.mplywacz.demo.services;

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.dto.mappers.Mapper;
import org.mplywacz.demo.model.Driver;
import org.mplywacz.demo.repositories.DriverRepo;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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
        return driverRepo.save(driverMapper.convertDto(driverDto));
    }

    @Override public Driver getDriver(String id) {
        var idVal = parseId(id);
        return driverRepo.findById(idVal).orElseThrow(
                () -> new NoSuchElementException("Element with given id: " + id + " not found"));
    }

    @Override public String deleteDriver(String id) {
        var idVal = parseId(id);
        driverRepo.deleteById(idVal);
        return "deleted driver with id: " + id;
    }

    private Long parseId(String sId) {
        var idVal = Long.MIN_VALUE;
        try {
            idVal = Long.parseLong(sId);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException("given value cannot be parsed to Long: " + sId);
        }
        return idVal;
    }
}
