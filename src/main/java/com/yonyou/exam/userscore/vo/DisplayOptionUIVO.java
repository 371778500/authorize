package com.yonyou.exam.userscore.vo;

import java.io.Serializable;

public class DisplayOptionUIVO implements Serializable {

	public DisplayOptionUIVO() {
		super();
	}

	public DisplayOptionUIVO(String optionkey, String optionvalue) {
		super();
		this.option_key = optionkey;
		this.option_value = optionvalue;
	}

	/**
	 * @return option_key
	 */
	public String getOption_key() {
		return option_key;
	}

	/**
	 * @param option_key
	 *            要设置的 option_key
	 */
	public void setOption_key(String option_key) {
		this.option_key = option_key;
	}

	/**
	 * @return option_value
	 */
	public String getOption_value() {
		return option_value;
	}

	/**
	 * @param option_value
	 *            要设置的 option_value
	 */
	public void setOption_value(String option_value) {
		this.option_value = option_value;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3122738016041443113L;
	private String option_key;
	private String option_value;
}
