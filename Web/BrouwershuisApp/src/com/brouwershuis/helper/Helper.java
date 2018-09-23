package com.brouwershuis.helper;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.brouwershuis.controller.EmployeeController;

public class Helper {

	private static final Logger LOGGER = Logger.getLogger(Helper.class);

	private static final String TIME_FORMAT = "HH:mm";
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	static SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

	public static String formatDate(Date date) {

		try {
			return dateFormat.format(date);

		} catch (Exception ex) {
			return dateFormat.format(new Date());
		}
	}

	public static Date formatDate(String dateString) {
		Date date;
		try {
			date = dateFormat.parse(dateString);
			return date;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static Date formatDateCatchExcpetion(String dateString) throws ParseException {
		Date date;

		date = dateFormat.parse(dateString);
		return date;
	}

	/*
	 * Week starts from Sunday
	 */
	public static Date[] getFirstAndLastDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		int dayNum = cal.getFirstDayOfWeek();

		cal.set(Calendar.DAY_OF_WEEK, dayNum - (7 - dayNum) - 1);
		Date start = cal.getTime();

		cal.set(Calendar.DAY_OF_WEEK, dayNum + (7 - dayNum));
		Date end = cal.getTime();

		Date[] dates = new Date[2];
		dates[0] = start;
		dates[1] = end;

		return dates;
	}

	public static Date formaDatetTime(String t) {
		try {
			Date time = timeFormat.parse(t);
			return time;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());

			String g = "";
		}
		return null;
	}

	public static Time formatTime(String formattedTime) {
		try {
			String newTime = "";
			if (formattedTime.length() != 0) {
				String[] t = formattedTime.split(":");

				if (t.length == 1) {
					newTime = t[0] + ":00:00";
				} else if (t.length == 2) {
					newTime = t[0] + ":" + t[1] + ":00";
				}
			} else {
				return null;
			}

			Time time = Time.valueOf(newTime);
			return time;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			String g = "";
		}
		return null;
	}

	public static String formatTime(Date t) {
		try {
			return timeFormat.format(t);
		} catch (Exception ex) {
			String g = "";
		}
		return "00:00";
	}

	public static String formatTime(Time t) {
		try {
			if (t != null) {
				return timeFormat.format(t);
			}
			return null;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			String g = "";
		}
		return null;
	}

	public static int getSecondsFromTime(String formattedTime) {

		if (formattedTime != null && formattedTime.length() != 0) {
			int totalSeconds = 0;

			String[] t = formattedTime.split(":");
			
			if (t.length >= 1)
				totalSeconds = Integer.valueOf(t[0]) * 60 * 60;

			if (t.length == 2)
				totalSeconds = totalSeconds + Integer.valueOf(t[1]) * 60;

			return totalSeconds;
		}
		return 0;
	}

	public static String getTimeFromSeconds(int totalSeconds) {

		if (totalSeconds != 0) {

			int hours = totalSeconds / 3600;
			int minute = (totalSeconds % 3600) / 60;
			if (minute < 10) {
				return hours + ":0" + minute;
			} else {
				return hours + ":" + minute;
			}

		} else {
			return "00:00";
		}

	}

	public static boolean isEmptyTime(Date date) {

		if (date != null) {
			String formattedTime = timeFormat.format(date);

			String[] t = formattedTime.split(":");

			int hour = Integer.valueOf(t[0]);
			int minute = Integer.valueOf(t[1]);

			LocalTime localTime = LocalTime.of(hour, minute);
			long result = localTime.get(ChronoField.SECOND_OF_DAY);

			if (result == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
