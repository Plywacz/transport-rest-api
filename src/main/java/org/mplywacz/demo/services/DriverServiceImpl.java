package org.mplywacz.demo.services;

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.dto.mappers.Mapper;
import org.mplywacz.demo.model.Driver;
import org.mplywacz.demo.repositories.DriverRepo;
import org.springframework.dao.DataAccessException;
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

    @Override public Driver getDriver(Long id) {
        return driverRepo.findById(id).orElseThrow(
                () -> new NoSuchElementException("Driver with given id: " + id + " not found"));
    }

    @Override public String deleteDriver(Long id) {
        try {
            driverRepo.deleteById(id);
        }catch (DataAccessException e){
            throw new NoSuchElementException("Driver with given id: " + id + " not found, so we couldn't delete");
        }
        return "deleted driver with id: " + id;
    }


}
