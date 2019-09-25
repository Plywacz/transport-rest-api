package org.mplywacz.demo.services;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.dto.mappers.TransitMapper;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.TransitRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransitServiceImplTest {

    public static final String SOURCE_ADDRESS = "ul. Zakręt 8, Poznań";
    public static final String DESTINATION_ADDRESS = "Złota 44, Warszawa";
    public static final LocalDate DATE = LocalDate.parse(("2018-11-10"));
    public static final BigDecimal PRICE = BigDecimal.valueOf(450);
    public static final BigDecimal WRONG_PRICE = BigDecimal.valueOf(-1);
    @Mock
    TransitRepo transitRepo;

    @Mock
    TransitMapper transitMapper;

    TransitService transitService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        transitService = new TransitServiceImpl(transitRepo, transitMapper);
    }

    @Test
    public void addTransitHappyPath() {
        //given
        var transitDto = new TransitDto();
        transitDto.setSourceAddress(SOURCE_ADDRESS);
        transitDto.setDestinationAddress(DESTINATION_ADDRESS);
        transitDto.setPrice(PRICE);
        transitDto.setDate(DATE);

        var savedTransit = new Transit();
        savedTransit.setSourceAddress(transitDto.getSourceAddress());
        savedTransit.setDestinationAddress(transitDto.getDestinationAddress());
        savedTransit.setPrice(transitDto.getPrice());
        savedTransit.setDate(transitDto.getDate());
        savedTransit.setDistance(BigDecimal.valueOf(12));

        //when
        when(transitRepo.save(any())).thenReturn(savedTransit);
        var returned = transitService.addTransit(transitDto);

        //then
        assertEquals(returned.getSourceAddress(), transitDto.getSourceAddress());
        assertNotNull(returned.getDistance());
    }

        //impossible as we have validation in controller

//    @Test(expected = IllegalArgumentException.class)
//    public void addTransitWithIncorrectInfo() {
//        var transitDto = new TransitDto();
//        transitDto.setSourceAddress(SOURCE_ADDRESS);
//        transitDto.setDestinationAddress(DESTINATION_ADDRESS);
//        transitDto.setPrice(WRONG_PRICE);
//        transitDto.setDate(DATE);
//
//        var savedTransit = new Transit();
//
//        //when
//        when(transitRepo.save(any())).thenReturn(savedTransit);
//        var returned = transitService.addTransit(transitDto);
//    }

    //impossible to pass null argument to the service since there is validation
//    @Test(expected = IllegalArgumentException.class)
//    public void addNullTransitDto() {
//        transitService.addTransit(null);
//    }

    @Test
    public void getRangeReportHappy() throws JSONException {
        //given
        var rangeRepDto = new RangeReportDto();
        rangeRepDto.setStartDate(LocalDate.of(2016, 1, 1));
        rangeRepDto.setEndDate(LocalDate.of(2017, 1, 1));

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

        var json = transitService.getRangeReport(rangeRepDto);

        assertEquals(json.getString("total_distance"), "600");
        assertEquals(json.getString("total_price"), "600");
    }

    @Test(expected = RuntimeException.class)
    public void getRangeReportFaultyResultFromDB() throws JSONException {
        //given
        var rangeRepDto = new RangeReportDto();
        rangeRepDto.setStartDate(LocalDate.of(2016, 1, 1));
        rangeRepDto.setEndDate(LocalDate.of(2017, 1, 1));

        var t1 = new Transit();
        t1.setDistance(BigDecimal.valueOf(100));
        t1.setPrice(BigDecimal.valueOf(100));

        var t2 = new Transit();
        t2.setDistance(BigDecimal.valueOf(200));
        t2.setPrice(BigDecimal.valueOf(200));

        var t3 = new Transit();
        t3.setDistance(BigDecimal.valueOf(-300));
        t3.setPrice(BigDecimal.valueOf(300));

        var transits = new HashSet<Transit>();
        transits.add(t1);
        transits.add(t2);
        transits.add(t3);

        when(transitRepo.selectTransitsInDateRange(any(), any())).thenReturn(transits);

        var json = transitService.getRangeReport(rangeRepDto);

    }
    //no need fot this test 'cause data validation is made in upper layer
//    @Test(expected = IllegalArgumentException.class)
//    public void getRangeReportIllegalArg() {
//        var rangeRepDto = new RangeReportDto();
//        rangeRepDto.setStartDate(null);
//        rangeRepDto.setEndDate(LocalDate.of(2017, 1, 1));
//
//        transitService.getRangeReport(rangeRepDto);
//    }

    //i think there is no need te test  getMonthlyReport because it almost entirely utilises external libraries that are already well tested
}