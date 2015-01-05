package com.keeping.business.common.util;

public class TimeUtil {

	public static java.sql.Timestamp transferFromUtilToSql(java.util.Date sourceDate){
		
		if(sourceDate != null){
			System.out.println("Time is:" + sourceDate.getTime());
			return new java.sql.Timestamp(sourceDate.getTime());
		} else {
			return null;
		}
	}
	
	public static java.util.Date transferFromSqlToUtil(java.sql.Timestamp sourceDate){
		
		if(sourceDate != null){
			return new java.util.Date(sourceDate.getTime());
		} else {
			return null;
		}
	}
}
