package com.example.demo.binding;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class StringStripJsonDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getValueAsString();
        System.out.println("Source value: " + value);

        if (value == null) {
            return null;
        }

        String valueStripped = value.strip();

        return valueStripped.length() != 0 ? valueStripped : null;
    }
}
