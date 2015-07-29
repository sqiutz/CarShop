package com.keeping.business.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtil {

	public static java.sql.Timestamp transferFromUtilToSql(
			java.util.Date sourceDate) {

		if (sourceDate != null) {
			return new java.sql.Timestamp(sourceDate.getTime());
		} else {
			return null;
		}
	}

	public static java.util.Date transferFromSqlToUtil(
			java.sql.Timestamp sourceDate) {

		if (sourceDate != null) {
			return new java.util.Date(sourceDate.getTime());
		} else {
			return null;
		}
	}

	public static java.sql.Date transferFromUtilToSqlDate(
			java.util.Date sourceDate) {

		if (sourceDate != null) {
			return new java.sql.Date(sourceDate.getTime());
		} else {
			return null;
		}
	}

	public static java.util.Date transferFromSqlToUtilDate(
			java.sql.Date sourceDate) {

		if (sourceDate != null) {
			return new java.util.Date(sourceDate.getTime());
		} else {
			return null;
		}
	}

	public static java.util.Date transferFromStringToUtilDate(String sourceDate) {

		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		if (sourceDate != null) {
			try {
				return fmt.parse(sourceDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		return null;
	}
}
