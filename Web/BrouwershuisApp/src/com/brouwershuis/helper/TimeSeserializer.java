package com.brouwershuis.helper;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class TimeSeserializer implements JsonSerializer<Time> {
	
	private static final Logger LOGGER = Logger.getLogger(TimeSeserializer.class);
	
	private static final String TIME_FORMAT = "HH:mm";

	@Override
	public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			String time = sdf.format(src);
			return context.serialize(time);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}
}
