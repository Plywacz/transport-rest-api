package org.mplywacz.demo.controllers;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.exceptions.IncorrectLocationException;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.services.TransitService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransitControllerTest {
    MockMvc mockMvc;

    @Mock
    TransitService transitService;

    @InjectMocks
    TransitController transitController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transitController)
                .setControllerAdvice(new CustomExceptionHandler()).build();
    }

    @Test
    public void addTransitHappyPath() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "ul. Zakręt 8, Poznań")
                .put("destination_address", "Złota 44, Warszawa")
                .put("price", "450")
                .put("date", "2018-03-15");

        var savedTransit = new Transit();
        savedTransit.setSourceAddress(inputJson.getString("source_address"));
        savedTransit.setDestinationAddress(inputJson.getString("destination_address"));
        savedTransit.setPrice(BigDecimal.valueOf(inputJson.getDouble("price")));
        savedTransit.setDate(LocalDate.parse(inputJson.getString("date")));
        savedTransit.setDistance(BigDecimal.valueOf(12));

        when(transitService.addTransit(any(TransitDto.class))).thenReturn(savedTransit);

        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.distance", Matchers.equalTo(12)));

    }

    @Test
    public void addTransitIncorrectAddress() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "ul. xsafasfsafasfas fas")
                .put("destination_address", "fsafsafas")
                .put("price", "450")
                .put("date", "2018-03-15");

        when(transitService.addTransit(any(TransitDto.class))).thenThrow(new IncorrectLocationException("EXC"));

        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson.toString()))
                .andExpect(status().is4xxClientError());
    }

}