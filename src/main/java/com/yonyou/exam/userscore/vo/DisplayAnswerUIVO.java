package com.yonyou.exam.userscore.vo;

import java.io.Serializable;

import com.yonyou.exam.userscore.entity.UserAnswerVO;

public class DisplayAnswerUIVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3670650135218446947L;

	private String pk_answer;
	private Integer answer_status;

	public DisplayAnswerUIVO() {
		super();
	}

	public DisplayAnswerUIVO(UserAnswerVO answer) {
		super();
		if (null == answer)
			return;
		this.pk_answer = answer.getPk_exam_user_answer();
		this.answer_status = answer.getAnswer_status();
	}

	/**
	 * @return pk_answer
	 */
	public String getPk_answer() {
		return pk_answer;
	}

	/**
	 * @param pk_answer
	 *            要设置的 pk_answer
	 */
	public void setPk_answer(String pk_answer) {
		this.pk_answer = pk_answer;
	}

	/**
	 * @return answer_status
	 */
	public Integer getAnswer_status() {
		return answer_status;
	}

	/**
	 * @param answer_status
	 *            要设置的 answer_status
	 */
	public void setAnswer_status(Integer answer_status) {
		this.answer_status = answer_status;
	}

}
