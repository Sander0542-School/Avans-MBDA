package nl.avans.mbda.weatherapp.models;

import java.io.IOException;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Converter {

    private static ObjectMapper mapper;

    public static <T> T fromJsonString(String json, Class<T> type) throws IOException {
        return getObjectMapper().readValue(json, type);
    }

    public static String toJsonString(Object obj) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(obj);
    }

    private static ObjectMapper getObjectMapper() {
        if (mapper == null) mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }
}
