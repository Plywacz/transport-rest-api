package org.mplywacz.demo.dto.mappers;
/*
Author: BeGieU
Date: 16.09.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.exceptions.IncorrectLocationException;
import org.mplywacz.demo.model.Transit;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class TransitMapperImpl implements TransitMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    //todo Externalize properties like api link to app.properties file
    public static final UriTemplate uriTemplate = new UriTemplate(
            "http://open.mapquestapi.com/directions/v2/route?key=wNq7cvi0pFSuG50szmeFaYI6VH9c2KEP&unit=k&from={from}&to={to}");

    public Transit convertTransitDto(TransitDto dto) {
        var transit = new Transit();
        transit.setSourceAddress(dto.getSourceAddress());
        transit.setDestinationAddress(dto.getDestinationAddress());
        transit.setDate(dto.getDate());
        transit.setPrice(dto.getPrice());

        //handling checked exception, converting them to unchecked
        try {
            transit.setDistance(calculateDistance(
                    dto.getSourceAddress(),dto.getDestinationAddress()));
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return transit;
    }

    //calculating distance using OpenDirectionsApi
    //todo try to make this async
    private BigDecimal calculateDistance(String fromAdr,String toAdr) throws IOException, IncorrectLocationException {
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("from", fromAdr);
        uriVariables.put("to", toAdr);
        var expandedURI = uriTemplate.expand(uriVariables);

        JsonNode routeInfoNode = objectMapper.readTree(expandedURI.toURL());
        JsonNode distanceNode = routeInfoNode.get("route").get("distance");

        if (distanceNode == null) {
            //this exception will be caught only by method in CustomException handler, so bigDecimal won't be returned but that
            //what handling method returns
            //same thing happens in getRangeReport Method
            throw new IncorrectLocationException("couldn't calculate distance given source or destination address is unacceptable\n" +
                    "  sourceAddress: " + fromAdr + "\n" +
                    "  destinationAddress: " + toAdr);
        }

        return new BigDecimal(distanceNode.asText());
    }
}
