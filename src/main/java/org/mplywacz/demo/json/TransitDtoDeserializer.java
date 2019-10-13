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

import java.io.IOException;

public class TransitDtoDeserializer extends StdDeserializer<TransitDto> {

    public TransitDtoDeserializer() {
        this(null);
    }

    public TransitDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TransitDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);
        if (node.size() != 5) {
            throw new IllegalArgumentException("incorrect json object passed, correct format:" +
                    "{\n" +
                    "  \"driver_id\":      \"1\",\n" +
                    "  \"source_address\":      \"ul. Zakręt 8, Poznań\",\n" +
                    "  \"destination_address\": \"Złota 44, Warszawa\",\n" +
                    "  \"price\":               450,\n" +
                    "  \"date\":                \"2018-03-15\"\n" +
                    "}");
        }

        var transitDto = new TransitDto();
        transitDto.setDriverId(node.get("driver_id"));
        transitDto.setSourceAddress(node.get("source_address"));
        transitDto.setDestinationAddress(node.get("destination_address"));
        transitDto.setPrice(node.get("price"));
        transitDto.setDate(node.get("date"));

        return transitDto;
    }

}
