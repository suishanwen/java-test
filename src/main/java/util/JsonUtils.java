package util;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class JsonUtils {
    public JsonUtils() {
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));

        try {
            return objectMapper.readValue(json, clz);
        } catch (IOException var4) {
            throw new RuntimeException("Failed to deserialize json: " + var4.getMessage(), var4);
        }
    }

    public static <T> List<T> jsonToList(String json, Class<T> clz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));

        try {
            CollectionType e = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clz);
            return (List)objectMapper.readValue(json, e);
        } catch (IOException var4) {
            throw new RuntimeException("Failed to deserialize json to List: " + var4.getMessage(), var4);
        }
    }

    public static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static JsonNode toJsonNode(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readTree(json);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
}
