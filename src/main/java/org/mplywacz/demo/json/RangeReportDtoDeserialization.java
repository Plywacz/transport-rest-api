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
import org.mplywacz.demo.exceptions.IllegalDateInputException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
        try {
            dto.setStartDate(LocalDate.parse(node.get("start_date").asText()));
            dto.setEndDate(LocalDate.parse(node.get("end_date").asText()));
        }catch (DateTimeParseException e){
            throw new IllegalDateInputException("provide correct data format yyyy-mm-dd",e);
        }

        return dto;
    }
}
