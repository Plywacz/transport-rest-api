package org.mplywacz.demo.controllers;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mplywacz.demo.dto.DailyInfo;
import org.mplywacz.demo.dto.RangeReportDto;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.services.TransitService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void addTransitValidationFailed() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "") //blank
                .put("destination_address", "")
                .put("price", "450")
                .put("date", "2018-03-15");

        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson.toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTransitEmptyDto() throws Exception {
        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTransitWrongEntity() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("csa", "") //blank
                .put("csa", "")
                .put("vre", "450")
                .put("davrte", "2018-03-15");

        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content((inputJson).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTransitUnsuitedExtendedEntity() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "val") //blank
                .put("destination_address", "val")
                .put("price", "450")
                .put("date", "2018-03-15")
                .put("EXTENSION", "EXTENSION");

        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content((inputJson).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTransitParsingPrice() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "val")
                .put("destination_address", "val")
                .put("price", "s")
                .put("date", "2018-03-15");


        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content((inputJson).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTransitParsingDate() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "val")
                .put("destination_address", "val")
                .put("price", "1")
                .put("date", "wrong_date");


        mockMvc.perform(post("/api/transits/")
                .contentType(MediaType.APPLICATION_JSON)
                .content((inputJson).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportHappy() throws Exception {
        var inputJson = new JSONObject()
                .put("start_date", "2018-01-20")
                .put("end_date", "2018-01-25");


        when(transitService.getRangeReport(any(RangeReportDto.class))).thenReturn(
                new JSONObject()
                        .put("total_distance", "1")
                        .put("total_price", "1"));

        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson.toString()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.total_distance", Matchers.equalTo("1")))
                .andExpect(jsonPath("$.total_price", Matchers.equalTo("1")));
    }

    @Test
    public void getRangeReportValidationFailedCausedByEmptyDto() throws Exception {
        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportIllegalArgument() throws Exception {
        var content = new JSONObject()
                .put("start_date", "")
                .put("end_date", "");

        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content((content).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportUnsuitedExtendedEntity() throws Exception {
        var inputJson = new JSONObject()
                .put("start_date", "2018-01-20")
                .put("end_date", "2018-01-25")
                .put("unnecessary_extension", "some value");

        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content((inputJson).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportWrongEntity() throws Exception {
        var content = new JSONObject()
                .put("@1d", "f21")
                .put("f21", "vad");

        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content((content).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportWrongEntity2() throws Exception {
        var content = new JSONObject()
                .put("", "f21")
                .put("", "vad");

        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content((content).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportUnsuitedEntity() throws Exception {
        JSONObject inputJson = new JSONObject()
                .put("source_address", "21") //blank
                .put("destination_address", "21")
                .put("price", "450")
                .put("date", "2018-03-15");

        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content((inputJson).toString()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getRangeReportNull() throws Exception {
        mockMvc.perform(get("/api/reports/range")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getMonthlyReportHappy() throws Exception {
        var d1 = new DailyInfo();
        d1.setDate(LocalDate.of(2016, 1, 1));
        d1.setTotalDistance(BigDecimal.ONE);
        d1.setAvgDistance(2.5);
        d1.setAvgPrice(2.3);

        var d2 = new DailyInfo();
        d2.setDate(LocalDate.of(2014, 1, 1));
        d2.setTotalDistance(BigDecimal.ONE);
        d2.setAvgDistance(2.1);
        d2.setAvgPrice(2.345);

        var d3 = new DailyInfo();
        d3.setDate(LocalDate.of(2014, 1, 1));
        d3.setTotalDistance(BigDecimal.ONE);
        d3.setAvgDistance(2.1);
        d3.setAvgPrice(2.345);

        List<DailyInfo> list = new ArrayList<>();
        list.add(d1);
        list.add(d2);
        list.add(d3);

        when(transitService.getMonthlyReport()).thenReturn(list);

        mockMvc.perform(get("/api/reports/monthly"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(3)));
    }
}