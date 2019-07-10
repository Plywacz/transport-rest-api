package org.mplywacz.demo.services;
/*
Author: BeGieU
Date: 05.07.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.exceptions.IncorrectLocationException;
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
        //todo move this code to mapper  TransitMapper (TransitDto -> Transit)
        var transit = new Transit();
        transit.setSourceAddress(transitDto.getSourceAddress());
        transit.setDestinationAddress(transitDto.getDestinationAddress());
        transit.setDate(transitDto.getDate());
        transit.setPrice(transitDto.getPrice());

        //handling checked exception, converting them to unchecked
        try {
            transit.setDistance(calculateDistance(transitDto));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return transitRepository.save(transit);
    }

    //calculate distance using OpenDirectionsApi
    //todo try to make this async
    private BigDecimal calculateDistance(TransitDto transitDto) throws IOException, IncorrectLocationException {
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("from", transitDto.getSourceAddress());
        uriVariables.put("to", transitDto.getDestinationAddress());
        var expandedURI = uriTemplate.expand(uriVariables);

        JsonNode routeInfoNode = objectMapper.readTree(expandedURI.toURL());
        JsonNode distanceNode = routeInfoNode.get("route").get("distance");

        if (distanceNode == null) {
            throw new IncorrectLocationException("couldn't calculate distance given source or destination address is unacceptable\n" +
                    "  sourceAddress: " + transitDto.getSourceAddress() + "\n" +
                    "  destinationAddress: " + transitDto.getDestinationAddress());
        }

        return new BigDecimal(distanceNode.asText());
    }


}
