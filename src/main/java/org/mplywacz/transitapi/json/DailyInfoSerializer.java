package org.mplywacz.transitapi.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mplywacz.transitapi.dto.DailyInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Author: BeGieU
Date: 19.09.2019
*/
public class DailyInfoSerializer extends StdSerializer<DailyInfo> {

    public DailyInfoSerializer() {
        this(null);
    }

    private DailyInfoSerializer(Class<DailyInfo> di) {
        super(di);
    }

    @Override
    public void serialize(DailyInfo value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("date", convertDate(value.getDate()));
        gen.writeNumberField("totalDistance", value.getTotalDistance());
        gen.writeNumberField("avgDistance", value.getAvgDistance());
        gen.writeNumberField("avgPrice", value.getAvgPrice());
        gen.writeEndObject();

    }

    private String convertDate(LocalDate ld) {
        var formatter =  DateTimeFormatter.ofPattern("MMMM, d");
        var dateStr = formatter.format(ld);
        var strs = (dateStr.split(" "));

        switch (strs[1]) {
            case "1":
                strs[1] += "st";
                break;
            case "2":
                strs[1] += "nd";
                break;
            case "3":
                strs[1] += "rd";
            default:
                strs[1] += "th";
                break;
        }

        return String.join(" ", strs);
    }
}
