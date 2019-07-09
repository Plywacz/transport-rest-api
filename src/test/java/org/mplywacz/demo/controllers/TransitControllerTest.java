package org.mplywacz.demo.controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.services.TransitService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.sql.Date;

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

    static final String testTransitJsonString="{\n" +
            "  \"source_address\":      \"ul. Zakręt 8, Poznań\",\n" +
            "  \"destination_address\": \"Złota 44, Warszawa\",\n" +
            "  \"price\":               450,\n" +
            "  \"date\":                \"2018-03-15\"\n" +
            "}";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transitController)
                .setControllerAdvice(new CustomExceptionHandler()).build();
    }

    @Test
    public void addTransitHappyPath() throws Exception {
        var transitDto = new TransitDto();
        transitDto.setSourceAddress("ul. Zakręt 8, Poznań");
        transitDto.setDestinationAddress("Złota 44, Warszawa");
        transitDto.setPrice(BigDecimal.valueOf(450));
        transitDto.setDate(Date.valueOf("2011-10-11"));

        var savedTransit = new Transit();
        savedTransit.setSourceAddress(transitDto.getSourceAddress());
        savedTransit.setDestinationAddress(transitDto.getDestinationAddress());
        savedTransit.setPrice(transitDto.getPrice());
        savedTransit.setDate(transitDto.getDate());
        savedTransit.setDistance(BigDecimal.valueOf(12));

        when(transitService.addTransit(any(TransitDto.class))).thenReturn(savedTransit);

        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testTransitJsonString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.distance", Matchers.equalTo(12)));

    }

}