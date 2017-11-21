package com.yonyou.upesn.common.enums;

import com.yonyou.appbase.util.StrUtil;


public class TrueOrFalseEnum {
	
	private static final String CODE_TRUE= "1"; 
	private static final String CODE_FALSE= "0";
	
	private static final TrueOrFalseEnum TRUE = new TrueOrFalseEnum("是",CODE_TRUE);
	private static final TrueOrFalseEnum FALSE = new TrueOrFalseEnum("否",CODE_FALSE);
	
	private String name;
	private String code;
	public TrueOrFalseEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static TrueOrFalseEnum getTrueOrFalse(String code){
		if (StrUtil.isEmpty(code))
		{
			return null;
		}
		switch(code){
			case CODE_TRUE:return TRUE;
			case CODE_FALSE:return FALSE;
			default:throw new IllegalArgumentException("状态异常");
		}
	}

}
