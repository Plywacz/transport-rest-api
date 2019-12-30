package org.mplywacz.transitapi.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.transitapi.dto.DriverDto;
import org.mplywacz.transitapi.dto.mappers.Mapper;
import org.mplywacz.transitapi.exceptions.EntityAlreadyExistException;
import org.mplywacz.transitapi.exceptions.UnprocessableRequestException;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.repositories.DriverRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        var res = driverService.deleteDriver(1L);
        assertEquals("deleted driver with id: " + 1L, res);
    }

    //    @Test
    //    public void deleteDriver_noDriver() {
    //        when(
    //                driverRepo.deleteById(anyLong())
    //        ).thenThrow(new RuntimeException());
    //
    //        driverService.deleteDriver(1L);
    //    }


    @Test
    public void getDriverReportHappyPath() {
        Object[] totalDriverInfo = new Object[]{
                1L, //id
                "firstName",
                "lastName",
                new BigDecimal(100), //totalDistance
                new BigDecimal(100), //totalPrice
                new BigDecimal(100) //mostExpensiveTransit
        };
        List<Object[]> totalInfoList = new ArrayList<>();
        totalInfoList.add(totalDriverInfo);

        Object[] monthInfo1 = new Object[]{
                "2012-01-01",
                new BigDecimal(100), //totalMonthlyDistance
                new BigDecimal(100), //maxMonthlyDistance
                new BigDecimal(100), //maxMonthlyTransitPrice
        };

        Object[] monthInfo2 = new Object[]{
                "2018-01-01",
                new BigDecimal(100), //totalMonthlyDistance
                new BigDecimal(100), //maxMonthlyDistance
                new BigDecimal(100), //maxMonthlyTransitPrice
        };

        List<Object[]> monthlyDriverInfo = new ArrayList<>();
        monthlyDriverInfo.add(monthInfo1);
        monthlyDriverInfo.add(monthInfo2);

        when(
                driverRepo.getTotalReportForDriver(anyLong())
        ).thenReturn(totalInfoList);

        when(
                driverRepo.getReportPerMonthForDriver(anyLong())
        ).thenReturn(monthlyDriverInfo);

        var res = driverService.getDriverReport(1L);

        assertEquals("firstName", res.getFirstName());
        assertEquals("lastName", res.getLastName());
        assertEquals(new BigDecimal(100), res.getTotalInfo().getLongestTransit());
        assertEquals(2, res.getMonthlyInfos().size());
    }

    @Test(expected = NoSuchElementException.class)
    public void getDriverReport_noDriver() {
        Object[] totalDriverInfo = new Object[]{
                null,
                "mockValue"
        };
        List<Object[]> totalInfoList = new ArrayList<>();
        totalInfoList.add(totalDriverInfo);
        when(
                driverRepo.getTotalReportForDriver(anyLong())
        ).thenReturn(totalInfoList);

        driverService.getDriverReport(1L);
    }

    @Test
    public void updateDriverHappyPath() {
//todo !!!!!!
    }
}