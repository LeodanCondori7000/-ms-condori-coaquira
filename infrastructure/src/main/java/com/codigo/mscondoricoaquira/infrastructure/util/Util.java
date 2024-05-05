package com.codigo.mscondoricoaquira.infrastructure.util;

import com.codigo.mscondoricoaquira.domain.aggregates.dto.EmpresaDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static <T> String convertirAString(T object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T convertirDesdeString( String json, Class<T> tipoClase){
        try {
            ObjectMapper objectMapper= new ObjectMapper();
            return objectMapper.readValue(json,tipoClase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    /*public static String convertirAString(EmpresaDto empresaDto){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(empresaDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
