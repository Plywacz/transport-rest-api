package org.mplywacz.transitapi.services;
/*
Author: BeGieU
Date: 27.09.2019
*/

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mplywacz.transitapi.exceptions.IncorrectLocationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class OpenDirectionsApiDistanceCalculator implements DistanceCalculator {
    private final ObjectMapper objectMapper;
    private final UriTemplate uriTemplate;

    public OpenDirectionsApiDistanceCalculator(@Qualifier("defaultObjectMapper") ObjectMapper defaultObjectMapper,
                                               UriTemplate uriTemplate) {
        this.objectMapper = defaultObjectMapper;
        this.uriTemplate = uriTemplate;
    }

    @Override
    public BigDecimal calculateDistance(String fromAdr, String toAdr) throws IOException {
        var uriVariables = new HashMap<String, String>();
        uriVariables.put("from", fromAdr);
        uriVariables.put("to", toAdr);
        var expandedURI = uriTemplate.expand(uriVariables);

        JsonNode routeInfoNode = objectMapper.readTree(expandedURI.toURL());
        JsonNode distanceNode = routeInfoNode.get("route").get("distance");

        if (distanceNode == null || distanceNode.toString().equals("0")) {
            throw new IncorrectLocationException("couldn't calculate distance given source or destination address is unacceptable\n" +
                    "  sourceAddress: " + fromAdr + "\n" +
                    "  destinationAddress: " + toAdr);
        }

        return new BigDecimal(distanceNode.asText());
    }
}
