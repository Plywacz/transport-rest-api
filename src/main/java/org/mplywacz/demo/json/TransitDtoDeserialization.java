package org.mplywacz.demo.json;
/*
Author: BeGieU
Date: 05.07.2019
*/

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mplywacz.demo.dto.TransitDto;
import org.mplywacz.demo.exceptions.IllegalDateInputException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

public class TransitDtoDeserialization extends StdDeserializer<TransitDto> {

    public TransitDtoDeserialization() {
        this(null);
    }

    public TransitDtoDeserialization(Class<?> vc) {
        super(vc);
    }

    @Override public TransitDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        var transit = new TransitDto();

            transit.setSourceAddress(node.get("source_address").asText());
            transit.setDestinationAddress(node.get("destination_address").asText());
            transit.setPrice(BigDecimal.valueOf(node.get("price").asDouble()));
        try {
            transit.setDate(Date.valueOf(node.get("date").asText()));
        }catch (IllegalArgumentException e){
            throw new IllegalDateInputException("provide correct date. yyyy-mm-dd",e);
        }

        return transit;
    }

}
