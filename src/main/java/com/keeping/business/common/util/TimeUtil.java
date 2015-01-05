package com.keeping.business.common.util;

public class TimeUtil {

	public static java.sql.Date transferFromUtilToSql(java.util.Date sourceDate){
		
		if(sourceDate != null){
			return new java.sql.Date(sourceDate.getTime());
		} else {
			return null;
		}
	}
	
	public static java.util.Date transferFromSqlToUtil(java.sql.Date sourceDate){
		
		if(sourceDate != null){
			return new java.util.Date(sourceDate.getTime());
		} else {
			return null;
		}
	}
}
