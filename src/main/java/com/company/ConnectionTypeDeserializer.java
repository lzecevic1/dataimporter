package com.company;

import com.company.enums.ConnectionType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Optional;

public class ConnectionTypeDeserializer extends JsonDeserializer<ConnectionType> {
    @Override
    public ConnectionType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Optional<ConnectionType> type = ConnectionType.fromType(jsonParser.getValueAsString());
        if(type.isPresent()) {
            return type.get();
        }
        throw new JsonParseException(jsonParser, "Parsing error!");
    }
}
