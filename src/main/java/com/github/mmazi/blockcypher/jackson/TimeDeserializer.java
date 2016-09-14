package com.github.mmazi.blockcypher.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeDeserializer extends JsonDeserializer<Date> {

    private SimpleDateFormat simpleDateFormat;

    {
        // Ignore fractional seconds as they mess stuff up. Don't know how to fix this.
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public Date deserialize(JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = jp.getValueAsString().replaceFirst("0000Z$", "");
        try {
            return simpleDateFormat.parse(value);
        } catch (ParseException e) {
            throw InvalidFormatException.from(jp, "Can't parse date at offset " + e.getErrorOffset(), value, Date.class);
            //      throw new RuntimeException("Can't parse date at offset " + e.getErrorOffset(), e);
        }
    }
}