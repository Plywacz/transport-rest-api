package org.mplywacz.transitapi.json;
/*
Author: BeGieU
Date: 05.07.2019
*/

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mplywacz.transitapi.dto.TransitDto;

import java.io.IOException;

import static org.mplywacz.transitapi.json.DeserializerHelper.*;

public class TransitDtoDeserializer extends StdDeserializer<TransitDto> {

    public TransitDtoDeserializer() {
        this(null);
    }

    public TransitDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TransitDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode requestBodyNode = jp.getCodec().readTree(jp);
        if (requestBodyNode.size() != 5) {
            throw new IllegalArgumentException("incorrect json object passed, correct format:" );
        }

        //all this below static methods in TransitDto(...) are needed to prevent null pointer exceptions
        return new TransitDto(
                getNodeLongValue(requestBodyNode.get("driverId"), "you havent provided driver's id"),
                getNodeStringValue(requestBodyNode.get("sourceAddress"), "you haven't provided source address"),
                getNodeStringValue(requestBodyNode.get("destinationAddress"), "you havent provided destination address"),
                getNodeBigDecimalValue(requestBodyNode.get("price"), "price not provided"),
                buildDateFromNode(requestBodyNode.get("date"))
        );
    }

}