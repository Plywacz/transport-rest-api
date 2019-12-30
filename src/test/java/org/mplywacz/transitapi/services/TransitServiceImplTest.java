package org.mplywacz.transitapi.services;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mplywacz.transitapi.dto.DailyInfo;
import org.mplywacz.transitapi.dto.TransitDto;
import org.mplywacz.transitapi.dto.mappers.Mapper;
import org.mplywacz.transitapi.exceptions.EntityNotFoundException;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.model.Transit;
import org.mplywacz.transitapi.repositories.DriverRepo;
import org.mplywacz.transitapi.repositories.TransitRepo;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransitServiceImplTest {

    public static final String SOURCE_ADDRESS = "ul. Zakręt 8, Poznań";
    public static final String DESTINATION_ADDRESS = "Złota 44, Warszawa";
    public static final LocalDate DATE = LocalDate.parse(("2018-11-10"));
    public static final BigDecimal PRICE = BigDecimal.valueOf(450);
    public static final BigDecimal DISTANCE = BigDecimal.valueOf(1000);
    @Mock
    TransitRepo transitRepo;

    @Mock
    DriverRepo driverRepo;

    @Mock
    Mapper<TransitDto, Transit> transitMapper;

    @Mock
    DistanceCalculator distanceCalculator;


    TransitService transitService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transitService = new TransitServiceImpl(
                transitRepo,
                driverRepo,
                transitMapper,
                distanceCalculator);
    }

    @Test
    public void addTransitHappyPath() throws IOException {
        //given
        var transitDto = new TransitDto();
        final long driverId = 1L;
        transitDto.setDriverId(driverId);
        transitDto.setSourceAddress(SOURCE_ADDRESS);
        transitDto.setDestinationAddress(DESTINATION_ADDRESS);
        transitDto.setDate(LocalDate.now());
        transitDto.setPrice(BigDecimal.ONE);

        var incompleteTransit = new Transit(); //incomplete because doesn't have distance field and driver set.
        incompleteTransit.setSourceAddress(SOURCE_ADDRESS);
        incompleteTransit.setDestinationAddress(DESTINATION_ADDRESS);
        incompleteTransit.setPrice(PRICE);
        incompleteTransit.setDate(DATE);

        var driver = new Driver();
        driver.setId(driverId);
        driver.setEnrolledDate(LocalDate.now());
        final String driverFirstName = "Jon";
        driver.setFirstName(driverFirstName);
        final String driverLastName = "Doe";
        driver.setLastName(driverLastName);

        //when
        when(
                driverRepo.findById(any(Long.class))
        ).thenReturn(Optional.of(driver));

        when(
                transitMapper.convertDto(any(TransitDto.class))
        ).thenReturn(incompleteTransit);

        final int distance = 100;
        when(
                distanceCalculator.calculateDistance(any(String.class), any(String.class))
        ).thenReturn(new BigDecimal(distance));

        incompleteTransit.setId(1L);
        var completeTransit=incompleteTransit;
        when(
                transitRepo.save(any())
        ).thenReturn(completeTransit);

        //then
        var returned = transitService.addTransit(transitDto);

        assertEquals(1L, (long)returned.getId());
        assertEquals(SOURCE_ADDRESS, returned.getSourceAddress());
        assertEquals(DESTINATION_ADDRESS, returned.getDestinationAddress());
        assertEquals(BigDecimal.valueOf(distance), returned.getDistance());
        assertEquals(driverId, (long) returned.getDriver().getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void addTransit_noDriverFound() throws Exception {
        var transitDto = new TransitDto();
        transitDto.setDriverId(1L);

        when(
                driverRepo.findById(any(Long.class))
        ).thenReturn(Optional.empty());


        transitService.addTransit(transitDto);
    }

    @Test(expected = RuntimeException.class)
    public void addTransit_distanceCalculationFail() throws IOException {
        var transitDto = new TransitDto();
        transitDto.setDriverId(1L);

        var incompleteTransit = new Transit(); //incomplete because doesn't have distance field and driver set.
        incompleteTransit.setSourceAddress(SOURCE_ADDRESS);
        incompleteTransit.setDestinationAddress(DESTINATION_ADDRESS);
        incompleteTransit.setPrice(PRICE);
        incompleteTransit.setDate(DATE);

        when(
                driverRepo.findById(any(Long.class))
        ).thenReturn(Optional.of(new Driver()));

        when(
                transitMapper.convertDto(any(TransitDto.class))
        ).thenReturn(incompleteTransit);

        when(
                distanceCalculator.calculateDistance(any(), any())
        ).thenThrow(new IOException());

        transitService.addTransit(transitDto);
    }


    @Test
    public void getRangeReportHappy() throws JSONException {
        //given
        var startDate = "2012-01-02";
        var endDate = "2015-02-03";

        var t1 = new Transit();
        t1.setDistance(BigDecimal.valueOf(100));
        t1.setPrice(BigDecimal.valueOf(100));

        var t2 = new Transit();
        t2.setDistance(BigDecimal.valueOf(200));
        t2.setPrice(BigDecimal.valueOf(200));

        var t3 = new Transit();
        t3.setDistance(BigDecimal.valueOf(300));
        t3.setPrice(BigDecimal.valueOf(300));

        var transits = new HashSet<Transit>();
        transits.add(t1);
        transits.add(t2);
        transits.add(t3);

        when(transitRepo.selectTransitsInDateRange(any(), any())).thenReturn(transits);

        var json = transitService.getRangeReport(startDate, endDate);

        assertEquals("600.0", json.getString("total_distance"));
        assertEquals("600.0", json.getString("total_price"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRangeReport_IncorrectDatesOrder() throws JSONException {
        var earlierDate = "2012-01-02";
        var laterDate = "2015-02-03";

        transitService.getRangeReport(laterDate, earlierDate); // incorrect order of dates
    }

    @Test(expected = DateTimeParseException.class)
    public void getRangeReport_WrongDateFormat() {
        var wrongDate = "WRONG_DATE_FORMAT";
        var laterDate = "2015-02-03";

        transitService.getRangeReport(wrongDate, laterDate); // incorrect order of dates
    }

    @Test
    public void getMonthlyReportHappyPath() {
        var dailyInfo1 = Mockito.mock(DailyInfo.class);
        var dailyInfo2 = mock(DailyInfo.class);

        var list = new ArrayList<DailyInfo>();
        list.add(dailyInfo1);
        list.add(dailyInfo2);


        when(
                transitRepo.getMonthlyInfoFromDB(any(LocalDate.class),any(LocalDate.class))
        ).thenReturn(list);
        var res=transitService.getMonthlyReport();

        assertEquals(2,res.size());
    }
}