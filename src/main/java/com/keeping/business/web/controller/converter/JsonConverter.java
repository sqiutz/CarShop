package com.keeping.business.web.controller.converter;

import java.util.List;

import com.keeping.business.common.util.JSONUtil;
import com.keeping.business.web.controller.model.WebResult;
import com.keeping.business.web.controller.model.WebResultList;
import com.keeping.business.web.controller.model.WebResultObject;

public class JsonConverter {
	/**
	 * 转换请求的参数
	 * 
	 * @param reqJsonStr
	 * @param clazz
	 * @return T
	 */
	public static <T> T getFromJsonString(String reqJsonStr, Class<T> clazz) {
		return JSONUtil.fromJson(reqJsonStr, clazz);
	}
	
	/**
	 * 转换请求的参数
	 * 
	 * @param reqJsonStr
	 * @param clazz
	 * @return T
	 */
	public static <T> T getFromJsonString(String reqJsonStr, Class<T> clazz, String datePattern) {
		return JSONUtil.fromJson(reqJsonStr, clazz, datePattern);
	}

	/**
	 * 转换返回的结果为Json字符串
	 * 
	 * @param code
	 * @param msg
	 * @return String
	 */
	public static String getResult(String code, String msg) {
		WebResult result = new WebResult();
		result.setCode(code);
		result.setMsg(msg);
		return JSONUtil.toJson(result);
	}

	/**
	 * 转换返回的结果为Json字符串
	 * 
	 * @param code
	 * @param msg
	 * @param t
	 * @return String
	 */
	public static <T> String getResult(String code, String msg, T t) {
		WebResultObject<T> result = new WebResultObject<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setObj(t);
		return JSONUtil.toJson(result);
	}

	/**
	 * 转换返回的结果为Json字符串
	 * 
	 * @param code
	 * @param msg
	 * @param tList
	 * @return String
	 */
	public static <T> String getResult(String code, String msg, List<T> tList) {
		WebResultList<T> result = new WebResultList<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setResList(tList);;
		return JSONUtil.toJson(result);
	}
	
	/**
	 * 转换返回的结果为Json字符串
	 * 
	 * @param code
	 * @param msg
	 * @param tList
	 * @return String
	 */
	public static <T> WebResultList<T> getResultObject(String code, String msg, List<T> tList) {
		WebResultList<T> result = new WebResultList<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setResList(tList);;
		return result;
	}
	
	public static <T> WebResultObject<T> getResultObject(String code, String msg, T tList) {
		WebResultObject<T> result = new WebResultObject<T>();
		result.setCode(code);
		result.setMsg(msg);
		result.setObj(tList);;
		return result;
	}
	public static WebResult getResultSignal(String code, String msg) {
		WebResult result = new WebResult();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
}
