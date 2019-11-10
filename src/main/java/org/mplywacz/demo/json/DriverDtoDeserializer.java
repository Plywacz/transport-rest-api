package org.mplywacz.demo.json;
/*
Author: BeGieU
Date: 08.10.2019
*/

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mplywacz.demo.dto.DriverDto;

import java.io.IOException;

public class DriverDtoDeserializer extends StdDeserializer<DriverDto> {
    public DriverDtoDeserializer() {
        this(DriverDtoDeserializer.class);
    }

    public DriverDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DriverDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        //todo: find out how is this line of code working, since it returns interface type and we bind it to the class???
        JsonNode requestBodyContent = jp.getCodec().readTree(jp);

        if (requestBodyContent.size() != 2) {
            throw new IllegalArgumentException("incorrect json object passed, correct format:" +
                    "{\n" +
                    "  \"first_name\": \"Jan\",\n" +
                    "  \"last_name\": \"Kowalski\",\n" +
                    "}");
        }

        return DriverDto.buildDriverDto(
                requestBodyContent.get("first_name"),
                requestBodyContent.get("last_name")
        );
    }
}
