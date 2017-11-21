package com.yonyou.exam.userscore.vo;

public class ContentUIVO {

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	private String questionid;

	/**
	 * @return questionid
	 */
	public String getQuestionid() {
		return questionid;
	}

	/**
	 * @param questionid
	 *            要设置的 questionid
	 */
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	private String answer;

}
