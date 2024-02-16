package com.youcode.interplanetary.GlobalConverters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Person;

import java.io.IOException;

public class AddressSerializer extends JsonSerializer<Person.Address> {
    @Override
    public void serialize(Person.Address address, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("street", address.getStreet());
        jsonGenerator.writeStringField("city", address.getCity());
        jsonGenerator.writeStringField("country", address.getCountry());
        jsonGenerator.writeStringField("zipcode", address.getZipcode());
        jsonGenerator.writeEndObject();
    }
}
