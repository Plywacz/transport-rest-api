package org.mplywacz.transitapi.json;
/*
Author: BeGieU
Date: 08.10.2019
*/

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.mplywacz.transitapi.model.Transit;

import java.io.IOException;

public class TransitDtoSerializer extends StdSerializer<Transit> {
    public TransitDtoSerializer() {
        this(Transit.class);
    }

    private TransitDtoSerializer(Class<Transit> di) {
        super(di);
    }

    @Override
    public void serialize(Transit value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("sourceAddress", value.getSourceAddress());
        gen.writeStringField("destinationAddress", value.getDestinationAddress());
        gen.writeStringField("price", value.getPrice().toString());
        gen.writeStringField("date", value.getDate().toString());
        gen.writeStringField("distance", value.getDistance().toString());
        gen.writeNumberField("driverId", value.getDriver().getId());
        gen.writeEndObject();

    }
}
