package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.model.Transit;
import org.mplywacz.demo.repositories.TransitRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class TransitServiceImpl implements TransitService {
    private final TransitRepo transitRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    //todo Externalize properties like api link to app.properties file
    public static final UriTemplate uriTemplate = new UriTemplate(
            "http://open.mapquestapi.com/directions/v2/route?key=wNq7cvi0pFSuG50szmeFaYI6VH9c2KEP&unit=k&from={from}&to={to}");

    public TransitServiceImpl(TransitRepo transitRepository) {
        this.transitRepository = transitRepository;
    }

    public Transit addTransit(final TransitDto transitDto) {
        try {
            var transit = new Transit();
            transit.setSourceAddress(transitDto.getSourceAddress());
            transit.setDestinationAddress(transitDto.getDestinationAddress());
            transit.setDate(transitDto.getDate()); // localdate -> sqldate
            transit.setPrice(transitDto.getPrice());

            transit.setDistance(calculateDistance(transitDto));

            return transitRepository.save(transit);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //todo handle this
        return null;
    }

    //private static final BigDecimal sd = new BigDecimal(0.7d, MathContext.DECIMAL64)

    //calculate distance using OpenDirectionsApi
    private BigDecimal calculateDistance(TransitDto transitDto) throws IOException {
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("from", transitDto.getSourceAddress());
        uriVariables.put("to", transitDto.getDestinationAddress());
        var expandedURI = uriTemplate.expand(uriVariables);

        final JsonNode node = objectMapper.readTree(expandedURI.toURL());
        double distance = node.get("route").get("distance").asDouble();

        return BigDecimal.valueOf(distance);
    }


}
