package com.wang.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by wangjf
 * Date 2019/1/3 11:27
 */
@Data
public class JsonData {

	private boolean ret;

	private String msg;

	private Object data;

	private JsonData(boolean ret) {
		this.ret = ret;
	}

	public static JsonData success(Object object, String msg) {
		JsonData jsonData = new JsonData(true);
		jsonData.setMsg(msg);
		jsonData.setData(object);
		return jsonData;
	}

	public static JsonData success(Object object) {
		JsonData jsonData = new JsonData(true);
		jsonData.setData(object);
		return jsonData;
	}

	public static JsonData success() {
		return new JsonData(true);
	}

	public static JsonData fail(String msg) {
		JsonData jsonData = new JsonData(false);
		jsonData.setMsg(msg);
		return jsonData;
	}

	public Map<String, Object> toMap() {
		HashMap<String, Object> result = new HashMap<>();
		result.put("ret", ret);
		result.put("msg", msg);
		result.put("data", data);
		return result;
	}

}
