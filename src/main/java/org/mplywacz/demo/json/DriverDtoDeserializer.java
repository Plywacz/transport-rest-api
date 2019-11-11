package org.mplywacz.demo.json;
/*
Author: BeGieU
Date: 08.10.2019
*/

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mplywacz.demo.dto.DriverDto;

import java.io.IOException;

import static org.mplywacz.demo.json.DeserializerHelper.getNodeStringValue;

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
        JsonNode requestBodyContent;
        try {
            requestBodyContent = jp.getCodec().readTree(jp);
        }
        catch (JsonParseException e) {
            throw new JsonParseException(jp,"couldn't parse given JSON");
        }

        if (requestBodyContent.size() != 2) {
            throw new IllegalArgumentException("incorrect json object passed, correct format:" +
                    "{\n" +
                    "  \"firstName\": \"Jan\",\n" +
                    "  \"lastName\": \"Kowalski\",\n" +
                    "}");
        }

        return new DriverDto(
                getNodeStringValue(requestBodyContent.get("firstName"), "you haven't provided proper first name"),
                getNodeStringValue(requestBodyContent.get("lastName"), "you haven't provided proper last name")
        );
    }
}
