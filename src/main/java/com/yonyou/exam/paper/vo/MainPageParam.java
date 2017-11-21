package com.yonyou.exam.paper.vo;

import java.util.Date;

public class MainPageParam {
	String search_text;
	Date start_time;
	Date end_time;
	String email;
	boolean isAnswered;

	/**
	 * @return isAnswered
	 */
	public boolean isAnswered() {
		return isAnswered;
	}

	/**
	 * @param isAnswered
	 *            要设置的 isAnswered
	 */
	public void setAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            要设置的 email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return search_text
	 */
	public String getSearch_text() {
		return search_text;
	}

	/**
	 * @param search_text
	 *            要设置的 search_text
	 */
	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}

	/**
	 * @return start_time
	 */
	public Date getStart_time() {
		return start_time;
	}

	/**
	 * @param start_time
	 *            要设置的 start_time
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	/**
	 * @return end_time
	 */
	public Date getEnd_time() {
		return end_time;
	}

	/**
	 * @param end_time
	 *            要设置的 end_time
	 */
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

}
