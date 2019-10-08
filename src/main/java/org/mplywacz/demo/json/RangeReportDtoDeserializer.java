package org.mplywacz.demo.json;
/*
Author: BeGieU
Date: 10.07.2019
*/

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.mplywacz.demo.dto.RangeReportDto;

import java.io.IOException;

public class RangeReportDtoDeserializer extends StdDeserializer<RangeReportDto> {

    public RangeReportDtoDeserializer() {
        this(null);
    }

    public RangeReportDtoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RangeReportDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        var dto = new RangeReportDto();

        if (node.size() != 2) {
            throw new IllegalArgumentException("provide inputJson in  in correct format, example :"
                    + " {\n" +
                    "  \"start_date\": \"2018-01-20\",\n" +
                    "  \"end_date\":  \"2018-01-25\"\n" +
                    "} \n this one was to long");
        }

        dto.setStartDate(node.get("start_date"));
        dto.setEndDate(node.get("end_date"));

        return dto;
    }
}
