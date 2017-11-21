package com.yonyou.appbase.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class StrUtil {

	public static boolean isEmpty(String str) {

		return (null == str || str.trim().length() == 0);
	}

	/**
	 * 将null转换为空字符串
	 * 
	 * @param value
	 * @return
	 */
	public static String nullToString(Object value) {
		if (value == null || value.equals("[]")
				|| value.toString().equals("[]"))
			return "";
		return value.toString();

	}

	/**
	 * 判断一个list是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmptyList(List list) {
		return null == list || list.size() == 0;
	}

	public static String getStingByArray(String[] str) {
		if (str.length == 0) {
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			if (!StrUtil.isEmpty(str[i])) {
				strBuffer.append("'" + str[i] + "',");
			}
		}

		return strBuffer.length() != 0 ? strBuffer.deleteCharAt(
				strBuffer.length() - 1).toString() : "''";
	}

	public static String getNotNullValue(String oldvalue) {
		if (isEmpty(oldvalue))
			return "";
		return oldvalue;
	}

	public static String getChineseChar(String str) {
		try {
			return new String(str.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
}