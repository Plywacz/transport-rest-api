package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 03.12.2019
*/

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mplywacz.transitapi.model.Driver;
import org.mplywacz.transitapi.repositories.DriverRepo;
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
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@WebAppConfiguration
public class TransitControllerIntegrationTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    DriverRepo driverRepo;

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
        //delete all driver then add on to make sure that driver existing in db has id = 1
        driverRepo.deleteAll();
        driverRepo.save(new Driver("Mock", "MOCK", LocalDate.now()));

        JSONObject inputJson = new JSONObject()
                .put("driverId", 1)
                .put("sourceAddress", "ul. Zakręt 8, Poznań")
                .put("destinationAddress", "Złota 44, Warszawa")
                .put("price", "450")
                .put("date", "2018-03-15");

        mockMvc
                .perform(post("/api/transits/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.driverId", Matchers.is(1)));

    }
}
