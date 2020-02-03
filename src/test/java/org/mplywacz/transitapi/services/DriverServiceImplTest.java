package org.mplywacz.transitapi.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.transitapi.dto.BasicDriverInfo;
import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.dto.MonthlyDriverInfo;
import org.mplywacz.transitapi.dto.TotalDriverInfo;
import org.mplywacz.transitapi.dto.mappers.Mapper;
import org.mplywacz.transitapi.exceptions.EntityAlreadyExistException;
import org.mplywacz.transitapi.exceptions.UnprocessableRequestException;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.repositories.DriverRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DriverServiceImplTest {
    public static final String DRIVER_FNAME = "Jon";
    public static final String DRIVER_LNAME = "Doe";
    @Mock
    DriverRepo driverRepo;
    @Mock
    Mapper<DriverDto, Driver> driverMapper;

    DriverService driverService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        driverService = new DriverServiceImpl(
                driverRepo,
                driverMapper);
    }

    @Test
    public void addDriverHappyPath() {
        var driverDto = new DriverDto(DRIVER_FNAME, DRIVER_LNAME);
        var incompleteDriver = new Driver(DRIVER_FNAME, DRIVER_LNAME, LocalDate.now());
        when(
                driverRepo.findDriverByFirstNameAndLastName(anyString(), anyString())
        ).thenReturn(null);

        when(
                driverMapper.convertDto(driverDto)
        ).thenReturn(incompleteDriver);

        final long driverId = 1L;
        incompleteDriver.setId(driverId);
        var completeDriver = incompleteDriver; //after id is set incomplete driver becomes complete
        when(
                driverRepo.save(any(Driver.class))
        ).thenReturn(completeDriver);

        var res = driverService.addDriver(driverDto);

        assertEquals(driverId, (long) res.getId());
        assertEquals(DRIVER_FNAME, res.getFirstName());
        assertEquals(DRIVER_LNAME, res.getLastName());
        assertEquals(0, res.getTransits().size());
    }

    @Test(expected = UnprocessableRequestException.class)
    public void addDriver_wrongNames() {
        var driverDto = new DriverDto("XASXASXAF#@F#F#RF322f23n1i", "!2wdascxz#QFD@$#F");

        driverService.addDriver(driverDto);
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void addDriver_driverAlreadyInDb() {
        var driverDto = new DriverDto(DRIVER_FNAME, DRIVER_LNAME);
        when(
                driverRepo.findDriverByFirstNameAndLastName(anyString(), anyString())
        ).thenReturn(mock(Driver.class));

        driverService.addDriver(driverDto);
    }

    @Test
    public void getDriverHappyPath() {

        var driver = new Driver();
        driver.setId(1L);
        when(
                driverRepo.findById(anyLong())
        ).thenReturn(Optional.of(driver));

        var res = driverService.getDriver(1L);

        assertEquals(1L, (long) res.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void getDriver_thereIsNoWantedDriver() {

        when(
                driverRepo.findById(anyLong())
        ).thenReturn(Optional.empty());

        driverService.getDriver(1L);
    }

    @Test
    public void deleteDriverHappyPath() {
        when(
                driverRepo.existsById(anyLong())
        ).thenReturn(true);

        var res = driverService.deleteDriver(1L);
        assertEquals("deleted driver with id: " + 1L, res);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteDriver_noDriver() {
        when(
                driverRepo.existsById(anyLong())
        ).thenReturn(false);

        driverService.deleteDriver(1L);
    }


    @Test
    public void getDriverReportHappyPath() {
        when(
                driverRepo.existsById(anyLong())
        ).thenReturn(true);

        var basicDriverInfo = new BasicDriverInfo(1L, DRIVER_FNAME, DRIVER_LNAME);
        when(
                driverRepo.getBasicDriverInfo(anyLong())
        ).thenReturn(basicDriverInfo);

        var monthlyDriverInfo0 = new MonthlyDriverInfo(
                "2012-12",
                BigDecimal.valueOf(12),
                BigDecimal.valueOf(13),
                BigDecimal.valueOf(14)
        );
        var monthlyDriverInfo1 = new MonthlyDriverInfo(
                "2013-11",
                BigDecimal.valueOf(15),
                BigDecimal.valueOf(16),
                BigDecimal.valueOf(17)
        );
        var monthlyInfoList = Arrays.asList(monthlyDriverInfo0, monthlyDriverInfo1);
        when(
                driverRepo.getMonthlyDriverInfo(anyLong())
        ).thenReturn(monthlyInfoList);

        final BigDecimal longestTransit = BigDecimal.valueOf(11);
        var totalDriverInfo = new TotalDriverInfo(
                BigDecimal.valueOf(10),
                longestTransit,
                BigDecimal.valueOf(12));
        when(
                driverRepo.getTotalDriverInfo(anyLong())
        ).thenReturn(totalDriverInfo);

        var res = driverService.getDriverReport(1L);

        assertEquals(DRIVER_FNAME, res.getBasicInfo().getFirstName());
        assertEquals(DRIVER_LNAME, res.getBasicInfo().getLastName());
        assertEquals(longestTransit, res.getTotalDriverInfo().getLongestTransit());
        assertEquals(2, res.getMonthlyDriverInfos().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void getDriverReport_noDriver() {
        when(
                driverRepo.existsById(anyLong())
        ).thenReturn(false);

        driverService.getDriverReport(1L);
    }

    @Test
    public void updateDriverHappyPath() {
        final String driverFname = "fname";
        final String driverLname = "lname";
        final long driverId = 1L;

        var driverDto = new DriverDto(driverFname, driverLname);

        var updatedDriver = new Driver();
        updatedDriver.setFirstName(driverFname);
        updatedDriver.setLastName(driverLname);
        updatedDriver.setEnrolledDate(LocalDate.now());

        when(
                driverRepo.findDriverByFirstNameAndLastName(anyString(), anyString())
        ).thenReturn(null);

        when(
                driverRepo.findById(anyLong())
        ).thenReturn(Optional.of(mock(Driver.class)));

        when(
                driverMapper.convertDto(any(DriverDto.class))
        ).thenReturn(updatedDriver);

        when(
                driverRepo.save(any(Driver.class))
        ).thenReturn(updatedDriver);

        var res = driverService.updateDriver(driverDto, driverId);

        assertEquals(driverId, (long) res.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void updateDriver_NoSuchElement() {
        when(
                driverRepo.findById(anyLong())
        ).thenReturn(Optional.empty());

        driverService.updateDriver(mock(DriverDto.class), 1L);
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void updateDriver_isAlreadyInDb() {
        final String driverFname = "fname";
        final String driverLname = "lname";

        var driverDto = new DriverDto(driverFname, driverLname);

        when(
                driverRepo.findById(anyLong())
        ).thenReturn(Optional.of(mock(Driver.class)));

        when(
                driverRepo.findDriverByFirstNameAndLastName(anyString(), anyString())
        ).thenReturn(mock(Driver.class));

        driverService.updateDriver(driverDto, 1L);
    }

    @Test(expected = UnprocessableRequestException.class)
    public void updateDriver_doesntContainOnlyLetters() {
        final String driverFname = "fname24f3qeewcqrg6543421xdawda";
        final String driverLname = "lname2reabv re ebdb d f f   f3rgwv";

        var driverDto = new DriverDto(driverFname, driverLname);

        when(
                driverRepo.findById(anyLong())
        ).thenReturn(Optional.of(mock(Driver.class)));

        when(
                driverRepo.findDriverByFirstNameAndLastName(anyString(), anyString())
        ).thenReturn(null);

        driverService.updateDriver(driverDto, 1L);
    }
}