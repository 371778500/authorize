package com.yonyou.exam.userscore.vo;

import java.io.Serializable;
import java.util.List;

public class CommitTestParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -996284696702224176L;
	private String test_id;
	private String test_time;
	private ContentUIVO[] contents;

	/**
	 * @return test_id
	 */
	public String getTest_id() {
		return test_id;
	}

	/**
	 * @param test_id
	 *            要设置的 test_id
	 */
	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	/**
	 * @return test_time
	 */
	public String getTest_time() {
		return test_time;
	}

	/**
	 * @param test_time
	 *            要设置的 test_time
	 */
	public void setTest_time(String test_time) {
		this.test_time = test_time;
	}

	public ContentUIVO[] getContents() {
		return contents;
	}

	public void setContents(ContentUIVO[] contents) {
		this.contents = contents;
	}

	
	
}
