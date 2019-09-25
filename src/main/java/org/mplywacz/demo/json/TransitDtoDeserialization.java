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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TransitDtoDeserialization extends StdDeserializer<TransitDto> {

    public TransitDtoDeserialization() {
        this(null);
    }

    public TransitDtoDeserialization(Class<?> vc) {
        super(vc);
    }

    @Override public TransitDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (!node.hasNonNull("source_address") ||
                !node.hasNonNull("destination_address") ||
                !node.hasNonNull("price") ||
                !node.hasNonNull("date") ||
                node.size() != 4) {
            throw new IllegalDateInputException("provide date in correct format, example :" +
                    "{\n" +
                    "  \"source_address\":      \"ul. Zakręt 8, Poznań\",\n" +
                    "  \"destination_address\": \"Złota 44, Warszawa\",\n" +
                    "  \"price\":               450,\n" +
                    "  \"date\":                \"2018-03-15\"\n" +
                    "}"
            );
        }

        var transit = new TransitDto();
        transit.setSourceAddress(node.get("source_address").asText());
        transit.setDestinationAddress(node.get("destination_address").asText());
        transit.setPrice(BigDecimal.valueOf(node.get("price").asDouble(-1)));

        try {
            transit.setDate(LocalDate.parse(node.get("date").asText()));
        }
        catch (DateTimeParseException e) {
            throw new IllegalDateInputException("provide correct date. yyyy-mm-dd", e);
        }

        return transit;
    }

}
