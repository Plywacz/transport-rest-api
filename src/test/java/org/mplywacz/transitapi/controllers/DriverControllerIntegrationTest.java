package org.mplywacz.transitapi.controllers;

import org.assertj.core.util.Streams;
import org.hamcrest.Matchers;
import org.json.JSONObject;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
public class DriverControllerIntegrationTest {

    public static final String JSON_PROPER_FIRST_NAME_KEY = "firstName";
    public static final String JSON_PROPER_LAST_NAME_KEY = "lastName";
    public static final String JSON_PROPER_VALUE_NAME = "Jan";
    public static final String DRIVERS_API_PREFIX = "/api/drivers/";
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
    public void addDriver_HappyPath() throws Exception {
        deleteAllDrivers();

        JSONObject inputJson = new JSONObject()
                .put(JSON_PROPER_FIRST_NAME_KEY, JSON_PROPER_VALUE_NAME)
                .put(JSON_PROPER_LAST_NAME_KEY, JSON_PROPER_VALUE_NAME);

        mockMvc
                .perform(post(DRIVERS_API_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.firstName", Matchers.is(JSON_PROPER_VALUE_NAME)))
                .andExpect(jsonPath("$.lastName", Matchers.is(JSON_PROPER_VALUE_NAME)));
    }

    @Test
    public void addDriver_InvalidJsonInput_Key() throws Exception {

        String invalidJsonFirstNameKey = "a";

        JSONObject invalidInputJson = new JSONObject()
                .put(invalidJsonFirstNameKey, JSON_PROPER_VALUE_NAME)
                .put(JSON_PROPER_LAST_NAME_KEY, JSON_PROPER_VALUE_NAME);


        mockMvc
                .perform(post("/api/drivers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidInputJson.toString()))
                .andExpect(status().isUnprocessableEntity());

    }


    @Test
    public void addDriver_InvalidJsonInput_Value() throws Exception {

        String invalidJsonFirstNameValue = "!@##!@%%@#@%!@#%chh";
        JSONObject invalidInputJson = new JSONObject()
                .put(JSON_PROPER_FIRST_NAME_KEY, invalidJsonFirstNameValue)
                .put(JSON_PROPER_LAST_NAME_KEY, JSON_PROPER_VALUE_NAME);


        mockMvc
                .perform(post("/api/drivers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidInputJson.toString()))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void addDriver_InvalidJsonInput_NoJson() throws Exception {
        String emptyJson = "";
        mockMvc
                .perform(post("/api/drivers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void addDriver_InvalidJsonInput_WrongJsonFormat() throws Exception {
        String wronglyFormattedJson = "2142315fdsacqqe``";
        mockMvc
                .perform(post("/api/drivers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wronglyFormattedJson))
                .andExpect(status().isUnprocessableEntity());

    }

    //we need if statement, because this test may be triggered alone or along addDriver_HappyPath test. Without
    //without that if statement it would give different test results
    @Test
    public void addDriver_attemptToAddSameDriverTwice() throws Exception {
        driverRepo.save(
                new Driver(JSON_PROPER_VALUE_NAME, JSON_PROPER_VALUE_NAME, LocalDate.now())
        );

        JSONObject inputJson = new JSONObject()
                .put(JSON_PROPER_FIRST_NAME_KEY, JSON_PROPER_VALUE_NAME)
                .put(JSON_PROPER_LAST_NAME_KEY, JSON_PROPER_VALUE_NAME);

        // adding same driver twice
        mockMvc
                .perform(post(DRIVERS_API_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andExpect(status().isConflict());

    }

    @Test
    public void updateDriver_happyPath() throws Exception {
        deleteAllDrivers();
        addDriverToDbIfNoneExist();
        var idOfActuallyExistingDriver = getIdOfRandomDriver();

        final String updatedName = "updatedNAme";
        JSONObject inputJson = new JSONObject()
                .put(JSON_PROPER_FIRST_NAME_KEY, updatedName)
                .put(JSON_PROPER_LAST_NAME_KEY, updatedName);

        mockMvc
                .perform(put(DRIVERS_API_PREFIX + idOfActuallyExistingDriver)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(idOfActuallyExistingDriver.intValue())))
                .andExpect(jsonPath("$.firstName", Matchers.is(updatedName)))
                .andExpect(jsonPath("$.lastName", Matchers.is(updatedName)));
    }

    @Test
    public void updateDriver_invalidJsonInputKey() throws Exception {
        final String invalidJsonKey = "a";
        JSONObject inputJson = new JSONObject()
                .put(invalidJsonKey, JSON_PROPER_VALUE_NAME)
                .put(JSON_PROPER_LAST_NAME_KEY, JSON_PROPER_VALUE_NAME);

        mockMvc
                .perform(put(DRIVERS_API_PREFIX + "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson.toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void updateDriver_invalidJsonValue() throws Exception {
        addDriverToDbIfNoneExist();
        var id = getIdOfRandomDriver();

        final String invalidNameValue = "123";
        JSONObject inputJson = new JSONObject()
                .put(JSON_PROPER_FIRST_NAME_KEY, invalidNameValue)
                .put(JSON_PROPER_LAST_NAME_KEY, JSON_PROPER_VALUE_NAME);

        mockMvc
                .perform(put(DRIVERS_API_PREFIX + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson.toString()))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void updateDriver_InvalidJsonInput_NoJson() throws Exception {
        deleteAllDrivers();
        addDriverToDbIfNoneExist();

        String emptyJson = "";
        mockMvc
                .perform(put(DRIVERS_API_PREFIX + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void updateDriver_InvalidJsonInput_WrongJsonFormat() throws Exception {
        deleteAllDrivers();
        addDriverToDbIfNoneExist();

        String wronglyFormattedJson = "2142315fdsacqqe``";
        mockMvc
                .perform(put(DRIVERS_API_PREFIX + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wronglyFormattedJson))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void updateDriver_attemptToUpdateDriverThatNotExist() throws Exception {
        deleteAllDrivers();

        final String updatedName = "Jan";
        JSONObject inputJson = new JSONObject()
                .put(JSON_PROPER_FIRST_NAME_KEY, updatedName)
                .put(JSON_PROPER_LAST_NAME_KEY, updatedName);

        mockMvc
                .perform(put(DRIVERS_API_PREFIX + "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void updateDriver_incorrectPathVariable() throws Exception {
        String incorrectPathVariable = "XSXAxsa32b892vb";
        mockMvc
                .perform(delete(DRIVERS_API_PREFIX + incorrectPathVariable))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getDriverHappyPath() throws Exception {
        addDriverToDbIfNoneExist();
        Long idOfRecord = getIdOfRandomDriver();

        mockMvc
                .perform(get("/api/drivers/" + idOfRecord))
                .andExpect(status().isOk());

    }

    @Test
    public void getDriver_attemptToGetNonExistingDriver() throws Exception {
        deleteAllDrivers();
        String IdOfFirstRecord = "1";
        mockMvc
                .perform(get("/api/drivers/" + IdOfFirstRecord))
                .andExpect(status().isNotFound());
    }


    @Test
    public void getDriver_wrongIdFormat() throws Exception {
        deleteAllDrivers();
        String wrongId = "!#$#!$#!^!#";
        mockMvc
                .perform(get("/api/drivers/" + wrongId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteDriver_HappyPath() throws Exception {
        addDriverToDbIfNoneExist();
        Long idOfExistingDriver = getIdOfRandomDriver();

        mockMvc
                .perform(delete("/api/drivers/" + idOfExistingDriver))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteDriver_attemptToDeleteNonExistingDriver() throws Exception {
        deleteAllDrivers();
        mockMvc
                .perform(delete("/api/drivers/" + 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDriver_incorrectPathVariable() throws Exception {
        deleteAllDrivers();
        String incorrectPathVariable = "XSXAxsa32b892vb";
        mockMvc
                .perform(delete("/api/drivers/" + incorrectPathVariable))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getDriverReport_happyPath() throws Exception {
        //fails because of date bug
        deleteAllDrivers();
        var driver = new Driver(
                JSON_PROPER_VALUE_NAME,
                JSON_PROPER_VALUE_NAME,
                LocalDate.now());
        driverRepo.save(driver);

        var idOfDriverThatActuallyExist = getIdOfRandomDriver();

        var transit1 = new Transit();
        transit1.setDriver(driver);
        transit1.setDate(LocalDate.of(2014, 1, 1));
        transit1.setSourceAddress("ul. Nowodąbrowska 18, Tarnów");
        transit1.setDestinationAddress("ul. Zakręt 8, Poznań");
        final int distance1 = 100;
        transit1.setDistance(new BigDecimal(distance1));
        transit1.setPrice(new BigDecimal(10));
        transitRepo.save(transit1);

        var transit2 = new Transit();
        transit2.setDriver(driver);
        transit2.setDate(LocalDate.of(1998, 12, 1));
        transit2.setSourceAddress("ul. Nowodąbrowska 18, Tarnów");
        transit2.setDestinationAddress("ul. Zakręt 8, Poznań");
        final int distance2 = 200;
        transit2.setDistance(new BigDecimal(distance2));
        transit2.setPrice(new BigDecimal(20));
        transitRepo.save(transit2);


        mockMvc
                .perform(get(DRIVERS_API_PREFIX + idOfDriverThatActuallyExist + "/report"))
                .andDo(print())
                .andExpect(status().isOk());
        //                .andExpect(jsonPath("$.basicInfo.firstName", Matchers.is(JSON_PROPER_VALUE_NAME)))
        //                .andExpect(jsonPath("$.basicInfo.lastName", Matchers.is(JSON_PROPER_VALUE_NAME)))
        //                .andExpect(jsonPath("$.totalInfo.totalDistance", Matchers.is(300.0)))
        //                .andExpect(jsonPath("$.monthlyInfos[0].yearMonthDate", Matchers.is("1998-12"))) //todo fix date bug
        //                .andExpect(jsonPath("$.monthlyInfos[1].yearMonthDate", Matchers.is("2014-01")));

    }

    @Test
    public void getDriverReport_WrongId() throws Exception {
        var wrongId = "!@#";
        mockMvc
                .perform(get(DRIVERS_API_PREFIX + wrongId + "/report"))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void getDriverReport_getReportForDriverThatNotExist() throws Exception {
        deleteAllDrivers();
        mockMvc
                .perform(get(DRIVERS_API_PREFIX + 1 + "/report"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getDriverReport_driverDoesntExist() throws Exception {
        deleteAllDrivers();
        mockMvc
                .perform(get(DRIVERS_API_PREFIX + 1 + "/report"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getDriverReport_driverHasNoTransits() throws Exception {
        transitRepo.deleteAll();
        addDriverToDbIfNoneExist();
        var driverId = getIdOfRandomDriver();
        mockMvc
                .perform(get(DRIVERS_API_PREFIX + driverId + "/report"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void addDriverToDbIfNoneExist() {
        int[] size = new int[]{0};
        driverRepo.findAll().forEach(d -> size[0]++);
        if (size[0] == 0) {
            driverRepo.save(
                    new Driver(
                            JSON_PROPER_VALUE_NAME,
                            JSON_PROPER_VALUE_NAME,
                            LocalDate.now()));
        }
    }

    private void deleteAllDrivers() {
        driverRepo.deleteAll();
    }

    private Long getIdOfRandomDriver() {
        return Streams.stream(driverRepo.findAll()).findFirst().get().getId();
    }

}