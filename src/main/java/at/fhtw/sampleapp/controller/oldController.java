package at.fhtw.sampleapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class oldController {
    private ObjectMapper objectMapper;

    public oldController() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
