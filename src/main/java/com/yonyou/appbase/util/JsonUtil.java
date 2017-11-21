package com.yonyou.appbase.util;

import java.util.List;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {

	public JsonUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String toJsonStr(Object object) {
		String result = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
		try {
			result = mapper.writeValueAsString(object);//
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

//	public static <T> String toJsonStr(List<T> list) {
//		JSONArray jsonArray = JSONArray.fromObject(list.toArray());
//		return jsonArray.toString();
//	}
}