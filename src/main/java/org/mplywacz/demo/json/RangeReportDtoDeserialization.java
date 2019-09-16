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
import java.sql.Date;

public class RangeReportDtoDeserialization extends StdDeserializer<RangeReportDto> {

    public RangeReportDtoDeserialization() {
        this(null);
    }

    public RangeReportDtoDeserialization(Class<?> vc) {
        super(vc);
    }

    @Override
    public RangeReportDto deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        var dto = new RangeReportDto();
        dto.setStartDate(Date.valueOf(node.get("start_date").asText()));
        dto.setEndDate(Date.valueOf(node.get("end_date").asText()));

        return dto;
    }
}
