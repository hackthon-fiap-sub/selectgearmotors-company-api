package br.com.selectgearmotors.company.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
        return mapper.readValue(json, clazz);
    }

    public static <T> T jsonToObject(String message, Class<T> clazz) {
        try {
            return mapper.readValue(message, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
