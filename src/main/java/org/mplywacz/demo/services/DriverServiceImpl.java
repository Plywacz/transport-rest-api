package org.mplywacz.demo.services;

import org.mplywacz.demo.dto.DriverDto;
import org.mplywacz.demo.dto.mappers.Mapper;
import org.mplywacz.demo.model.Driver;
import org.mplywacz.demo.repositories.DriverRepo;
import org.springframework.stereotype.Service;

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

    @Override public String deleteDriver(Long id) {
        driverRepo.deleteById(id);
        return "deleted driver with id: " + id;
    }
}
