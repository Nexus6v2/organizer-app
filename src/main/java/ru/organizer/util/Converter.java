package ru.organizer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class Converter {
    public static final XmlMapper XML_MAPPER = new XmlMapper();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T>T convertMapToObject(Map map, Class<T> klass) {
        return OBJECT_MAPPER.convertValue(map, klass);
    }

    @SneakyThrows
    public static <T>T convertXmlToObject(String xml, Class<T> klass) {
        return XML_MAPPER.readValue(xml, klass);
    }
    
}
