package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 03.12.2019
*/

import org.assertj.core.util.Streams;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.model.Transit;
import org.mplywacz.transitapi.repositories.DriverRepo;
import org.mplywacz.transitapi.repositories.TransitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
public class TransitControllerIntegrationTest {
    public static final String DRIVER_NAME = "Ian";
    public static final String API_TRANSITS_PREFIX = "/api/transits/";
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    DriverRepo driverRepo;

    @Autowired
    TransitRepo transitRepo;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testContextLoading() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("transitController"));
    }

    @Test
    public void addTransitHappyPath() throws Exception {
        addDriverIfNone();
        var driverId = getRandomDriverId();

        JSONObject inputJson = new JSONObject()
                .put("driverId", driverId)
                .put("sourceAddress", "ul. Zakręt 8, Poznań")
                .put("destinationAddress", "Złota 44, Warszawa")
                .put("price", "450")
                .put("date", "2018-03-15");

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.driverId", Matchers.is(driverId.intValue())))
                .andExpect(jsonPath("$.price", Matchers.is(450)))
                .andExpect(jsonPath("$.date", Matchers.is("2018-03-15")));

    }

    @Test
    public void addTransit_invalidJsonKey() throws Exception {
        addDriverIfNone();
        var driverId = getRandomDriverId();

        final String invalidJsonKey = "#@$@!TVS";
        JSONObject inputJson = new JSONObject()
                .put(invalidJsonKey, driverId)
                .put("sourceAddress", "ul. Zakręt 8, Poznań")
                .put("destinationAddress", "Złota 44, Warszawa")
                .put("price", "450")
                .put("date", "2018-03-15");

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void addTransit_invalidJsonValue() throws Exception {
        addDriverIfNone();
        var driverId = getRandomDriverId();

        final String invalidJsonValue = "xdacdac3vh";
        JSONObject inputJson = new JSONObject()
                .put("driverId", driverId)
                .put("sourceAddress", "ul. Zakręt 8, Poznań")
                .put("destinationAddress", "Złota 44, Warszawa")
                .put("price", invalidJsonValue)
                .put("date", "2018-03-15");

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void addDriver_distanceCalculationNotPossible_WrongAddresses() throws Exception {
        addDriverIfNone();
        var driverId = getRandomDriverId();

        final String wrongAddr = "nowhere";
        JSONObject inputJson = new JSONObject()
                .put("driverId", driverId)
                .put("sourceAddress", wrongAddr)
                .put("destinationAddress", wrongAddr)
                .put("price", "12")
                .put("date", "2018-03-15");

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void addTransit_EmptyJson() throws Exception {
        var emptyJson = "";

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void addTransit_wrongJsonFormat() throws Exception {
        var wrongJson = "12dewcf46h65454h";

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wrongJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());

    }


    @Test
    public void addTransit_driverDoesntExist() throws Exception {
        deleteAllDrivers();

        var idOfNonExistingDriver = 1;
        JSONObject inputJson = new JSONObject()
                .put("driverId", idOfNonExistingDriver)
                .put("sourceAddress", "ul. Zakręt 8, Poznań")
                .put("destinationAddress", "Złota 44, Warszawa")
                .put("price", "412")
                .put("date", "2018-03-15");

        mockMvc
                .perform(post(API_TRANSITS_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());

    }

    @Test
    public void getRangeReport_happyPath() throws Exception {
        var driver = new Driver(
                DRIVER_NAME,
                DRIVER_NAME,
                LocalDate.now());
        driverRepo.save(driver);

        final String date1 = "2019-01-01";
        final var inRangeDate = LocalDate.of(2019, 1, 4);
        final String date2 = "2019-02-02";

        var transit1 = new Transit();
        transit1.setDriver(driver);
        transit1.setDate(inRangeDate);
        transit1.setSourceAddress("ul. Nowodąbrowska 18, Tarnów");
        transit1.setDestinationAddress("ul. Zakręt 8, Poznań");
        final int distance1 = 100;
        transit1.setDistance(new BigDecimal(distance1));
        final int price1 = 10;
        transit1.setPrice(new BigDecimal(price1));
        transitRepo.save(transit1);

        var transit2 = new Transit();
        transit2.setDriver(driver);
        transit2.setDate(inRangeDate);
        transit2.setSourceAddress("ul. Nowodąbrowska 18, Tarnów");
        transit2.setDestinationAddress("ul. Zakręt 8, Poznań");
        final int distance2 = 200;
        transit2.setDistance(new BigDecimal(distance2));
        final int price2 = 20;
        transit2.setPrice(new BigDecimal(price2));
        transitRepo.save(transit2);


        mockMvc
                .perform(get(API_TRANSITS_PREFIX + "reports/range/" + date1 + "&&" + date2))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.total_price", Matchers.is(price1 + price2)))
                .andExpect(jsonPath("$.total_distance", Matchers.is(distance1 + distance2)));
    }

    @Test
    public void getRangeReport_wrongDate() throws Exception {
        var wrongDate = "12dewcf46h65454h";

        mockMvc
                .perform(get(API_TRANSITS_PREFIX + "reports/range/" + wrongDate + "&&" + wrongDate))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getRangeReport_noDate() throws Exception {
        var noDate = "";

        mockMvc
                .perform(get(API_TRANSITS_PREFIX + "reports/range/" + noDate + "&&" + noDate))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getRangeReport_badDateOrder() throws Exception {
        final String date1 = "2019-01-01";
        final String date2 = "2019-01-11";

        mockMvc
                .perform(get(API_TRANSITS_PREFIX + "reports/range/" + date2 + "&&" + date1))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getMonthlyReport_happyPath() throws Exception {
        // test may not work on the first day od the month, due to some weird behaviour of db related with dates
        addDriverIfNone();
        var driver = new Driver(
                DRIVER_NAME,
                DRIVER_NAME,
                LocalDate.now());
        driverRepo.save(driver);

        var transit1 = new Transit();
        transit1.setDriver(driver);
        transit1.setDate(
                LocalDate.of(
                        LocalDate.now().getYear(),
                        LocalDate.now().getMonth(),
                        2) //day of month should be set on 1, if we want this test to work properly on every date,
                                        // but there is date bug that sets day of month on the previous day (-1) than we want
        );
        transit1.setSourceAddress("ul. Nowodąbrowska 18, Tarnów");
        transit1.setDestinationAddress("ul. Zakręt 8, Poznań");
        final int distance1 = 100;
        transit1.setDistance(new BigDecimal(distance1));
        final int price1 = 10;
        transit1.setPrice(new BigDecimal(price1));
        transitRepo.save(transit1);

        var transit2 = new Transit();
        transit2.setDriver(driver);
        transit2.setDate(LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                2));
        transit2.setSourceAddress("ul. Nowodąbrowska 18, Tarnów");
        transit2.setDestinationAddress("ul. Zakręt 8, Poznań");
        final int distance2 = 200;
        transit2.setDistance(new BigDecimal(distance2));
        final int price2 = 20;
        transit2.setPrice(new BigDecimal(price2));
        transitRepo.save(transit2);

        mockMvc
                .perform(get(API_TRANSITS_PREFIX + "reports/monthly"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].totalDistance", Matchers.is((double)distance1 + distance2)))
                .andExpect(jsonPath("$[0].avgPrice", Matchers.is((double)(price1 + price2)/2)));

    }
    @Test
    public void getMonthlyReport_noReport() throws Exception {
        deleteAllDrivers(); //deletes all transits also => no report
        mockMvc
                .perform(get(API_TRANSITS_PREFIX + "reports/monthly"))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    private void deleteAllDrivers() {
        driverRepo.deleteAll();
    }

    private void addDriverIfNone() {
        if (Streams.stream(driverRepo.findAll()).count() == 0)
            driverRepo.save(new Driver(DRIVER_NAME, DRIVER_NAME, LocalDate.now()));
    }

    private Long getRandomDriverId() {
        var driverOpt = Streams.stream(driverRepo.findAll()).findFirst();
        return (driverOpt.orElseThrow()).getId();
    }
}
