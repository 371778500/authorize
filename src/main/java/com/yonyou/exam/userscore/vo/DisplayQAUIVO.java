package com.yonyou.exam.userscore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.userscore.entity.UserAnswerVO;

public class DisplayQAUIVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1057004750731157249L;

	private Integer question_num;
	private String question_title;
	private List<DisplayOptionUIVO> option_list = new ArrayList<DisplayOptionUIVO>();
	private String right_answer;
	private String right_answer_analysis;

	public DisplayQAUIVO() {
		super();
	}

	public DisplayQAUIVO(ExamQuestionVo question, UserAnswerVO answer) {
		super();
		if (null == question || null == answer) {
			return;
		}

		this.answer = StrUtil.getNotNullValue(answer.getAnswer());
		this.question_num = question.getQuestion_num();
		this.question_title = StrUtil.getNotNullValue(question
				.getQuestion_title());
		this.right_answer = StrUtil.getNotNullValue(question.getRight_answer());
		this.right_answer_analysis = StrUtil.getNotNullValue(question
				.getRight_answer_analysis());
		this.option_list = new ArrayList<DisplayOptionUIVO>();
		if (!StrUtil.isEmpty(question.getOption_a())) {
			this.option_list.add(new DisplayOptionUIVO("A", question
					.getOption_a()));
		}
		if (!StrUtil.isEmpty(question.getOption_b())) {
			this.option_list.add(new DisplayOptionUIVO("B", question
					.getOption_b()));
		}
		if (!StrUtil.isEmpty(question.getOption_c())) {
			this.option_list.add(new DisplayOptionUIVO("C", question
					.getOption_c()));
		}
		if (!StrUtil.isEmpty(question.getOption_d())) {
			this.option_list.add(new DisplayOptionUIVO("D", question
					.getOption_d()));
		}
		if (!StrUtil.isEmpty(question.getOption_e())) {
			this.option_list.add(new DisplayOptionUIVO("E", question
					.getOption_e()));
		}
		if (!StrUtil.isEmpty(question.getOption_f())) {
			this.option_list.add(new DisplayOptionUIVO("F", question
					.getOption_f()));
		}

	}

	/**
	 * @return question_num
	 */
	public Integer getQuestion_num() {
		return question_num;
	}

	/**
	 * @param question_num
	 *            要设置的 question_num
	 */
	public void setQuestion_num(Integer question_num) {
		this.question_num = question_num;
	}

	/**
	 * @return question_title
	 */
	public String getQuestion_title() {
		return question_title;
	}

	/**
	 * @param question_title
	 *            要设置的 question_title
	 */
	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}

	/**
	 * @return option_list
	 */
	public List<DisplayOptionUIVO> getOption_list() {
		return option_list;
	}

	/**
	 * @param option_list
	 *            要设置的 option_list
	 */
	public void setOption_list(List<DisplayOptionUIVO> option_list) {
		this.option_list = option_list;
	}

	/**
	 * @return right_answer
	 */
	public String getRight_answer() {
		return right_answer;
	}

	/**
	 * @param right_answer
	 *            要设置的 right_answer
	 */
	public void setRight_answer(String right_answer) {
		this.right_answer = right_answer;
	}

	/**
	 * @return right_answer_analysis
	 */
	public String getRight_answer_analysis() {
		return right_answer_analysis;
	}

	/**
	 * @param right_answer_analysis
	 *            要设置的 right_answer_analysis
	 */
	public void setRight_answer_analysis(String right_answer_analysis) {
		this.right_answer_analysis = right_answer_analysis;
	}

	/**
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            要设置的 answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	private String answer;

}
