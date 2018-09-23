package com.brouwershuis.helper;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SqlTimeSerializer extends JsonSerializer<java.sql.Time> {

	private static final String TIME_FORMAT = "HH:mm";
	
	// private final String format;
	public SqlTimeSerializer() {
		super();
	}

	@Override
	public void serialize(Time arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		arg1.writeString(new SimpleDateFormat(TIME_FORMAT).format(arg0));
	}

}
