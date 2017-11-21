package com.yonyou.exam.userscore.vo;

import java.io.Serializable;

import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.exam.userscore.entity.UserScoreVO;

public class UserScoreUIVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2542949742955583809L;

	private Integer duration;

	private Integer error_num;

	private boolean isPass;

	private Integer right_num;

	private Integer score;

	private Integer uncompletedcounts;

	private Integer checkanswer_status;
	

	public UserScoreUIVO(UserScoreVO answered, PaperMainVo paper) {
		super();
		if (null == answered) {
			return;
		}

		this.duration = answered.getTest_time();
		this.score = answered.getUser_score();
		this.right_num = answered.getRight_answer_num();
		this.error_num = answered.getError_answer_num();
		this.uncompletedcounts = answered.getUncompleteAnswer_answer_num();
		this.isPass = (answered.getUser_score() >= paper.getPass_score());
		this.checkanswer_status = Integer.decode(paper.getCheckanswer_status());
	}

	/**
	 * @return duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @return error_num
	 */
	public Integer getError_num() {
		return error_num;
	}

	/**
	 * @return right_num
	 */
	public Integer getRight_num() {
		return right_num;
	}

	/**
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @return uncompletedcounts
	 */
	public Integer getUncompletedcounts() {
		return uncompletedcounts;
	}

	/**
	 * @return isPass
	 */
	public boolean isPass() {
		return isPass;
	}

	/**
	 * @param duration
	 *            要设置的 duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @param error_num
	 *            要设置的 error_num
	 */
	public void setError_num(Integer error_num) {
		this.error_num = error_num;
	}

	/**
	 * @param isPass
	 *            要设置的 isPass
	 */
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	/**
	 * @param right_num
	 *            要设置的 right_num
	 */
	public void setRight_num(Integer right_num) {
		this.right_num = right_num;
	}

	/**
	 * @param score
	 *            要设置的 score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @param uncompletedcounts
	 *            要设置的 uncompletedcounts
	 */
	public void setUncompletedcounts(Integer uncompletedcounts) {
		this.uncompletedcounts = uncompletedcounts;
	}

	public Integer getCheckanswer_status() {
		return checkanswer_status;
	}

	public void setCheckanswer_status(Integer checkanswer_status) {
		this.checkanswer_status = checkanswer_status;
	}
}
