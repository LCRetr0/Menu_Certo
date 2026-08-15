package com.Retr0.MenuCerto.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class convertedados implements iConverterdados  {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obterdados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
