package com.yonyou.authorize.util;

import java.util.List;

public class StrUtil {
	
	public static boolean isEmpty(String str) {
		
		return (null == str || str.trim().length()== 0) ;
	}
	
	/**
	 * 将null转换为空字符串
	 * @param value
	 * @return
	 */
	public static String nullToString(Object value){
		if(value==null|| value.equals("[]")||value.toString().equals("[]"))
			return "";
		return value.toString();
		
		
		
	}
	/**
	 * 判断一个list是否为空
	 * @param value
	 * @return
	 */
	public static boolean isEmptyList(List list){
		return null==list || list.size()==0;
	}
	
	public static String getStingByArray(String[] str) {
		if (str.length == 0) {
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		for (int i=0; i<str.length; i++) {
			if (!StrUtil.isEmpty(str[i])) {
				strBuffer.append("'" + str[i] + "',");
			}
		}
		
		return strBuffer.length() != 0 ? strBuffer.deleteCharAt(strBuffer.length() - 1).toString() : "''";
	}
}