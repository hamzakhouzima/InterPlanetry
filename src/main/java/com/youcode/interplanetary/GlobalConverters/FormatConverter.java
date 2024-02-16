package com.youcode.interplanetary.GlobalConverters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
public class FormatConverter {


    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final XmlMapper XML_MAPPER = new XmlMapper();

    public static String toJson(Object object) throws Exception {
        return JSON_MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws Exception {
        return JSON_MAPPER.readValue(json, valueType);
    }

    public static String toXml(Object object) throws Exception {
        return XML_MAPPER.writeValueAsString(object);
    }

    public static <T> T fromXml(String xml, Class<T> valueType) throws Exception {
        return XML_MAPPER.readValue(xml, valueType);
    }
}
