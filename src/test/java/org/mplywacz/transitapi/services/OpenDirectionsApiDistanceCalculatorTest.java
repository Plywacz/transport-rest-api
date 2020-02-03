package org.mplywacz.transitapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;

public class OpenDirectionsApiDistanceCalculatorTest {
    @Mock
    ObjectMapper objectMapper;

    DistanceCalculator distanceCalculator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        distanceCalculator = new OpenDirectionsApiDistanceCalculator(
                objectMapper,
                new UriTemplate("http://open.mapquestapi.com/directions/v2/route?key=wNq7cvi0pFSuG50szmeFaYI6VH9c2KEP&unit=k&from={from}&to={to}"));
    }

    @Test
    void calculateDistanceHappy() throws IOException {

        //todo

//        var jsonValue="{" +
//                ""
//
//
//        var textNode=new TextNode()
//        when(
//                objectMapper.readTree(ArgumentMatchers.any(URL.class))
//        ).thenReturn()
    }
}