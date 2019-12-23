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

public class TransitSerializer extends StdSerializer<Transit> {
    public TransitSerializer() {
        this(Transit.class);
    }

    private TransitSerializer(Class<Transit> di) {
        super(di);
    }

    @Override
    public void serialize(Transit value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("sourceAddress", value.getSourceAddress());
        gen.writeStringField("destinationAddress", value.getDestinationAddress());
        gen.writeNumberField("price", value.getPrice());
        gen.writeStringField("date", value.getDate().toString());
        gen.writeNumberField("distance", value.getDistance());
        gen.writeNumberField("driverId", value.getDriver().getId());
        gen.writeEndObject();

    }
}
