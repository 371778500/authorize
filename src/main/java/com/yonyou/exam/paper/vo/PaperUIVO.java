package com.yonyou.exam.paper.vo;

import java.util.ArrayList;
import java.util.List;

import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.paper.entity.PaperMainVo;

public class PaperUIVO {
	class QuestionUIVO {
		class OptionUIVO {
			public OptionUIVO(String key, String content) {
				super();
				this.content = content;
				this.key = key;

			}

			private String content;// 选项内容
			private String key;// 选项主键

			public String getContent() {
				return content;
			}

			public String getKey() {
				return key;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public void setKey(String key) {
				this.key = key;
			}
		}

		private List<OptionUIVO> options = new ArrayList<OptionUIVO>();// 选项列表
		private String question;// 题干
		private Integer serial_numer;// 题号
		private Integer optiontype;// 选项类型
		private String  questionid;//问题ID

		public String getQuestionid() {
			return questionid;
		}

		public void setQuestionid(String questionid) {
			this.questionid = questionid;
		}

		public Integer getOptiontype() {
			return optiontype;
		}

		public void setOptiontype(Integer optiontype) {
			this.optiontype = optiontype;
		}

		public QuestionUIVO(ExamQuestionVo eq) {
			super();
			this.serial_numer = eq.getQuestion_num();
			this.optiontype = eq.getOptiontype();
			this.question = StrUtil.getNotNullValue(eq.getQuestion_title());
			this.questionid = eq.getPk_exam_exam_question();
			
			if (!StrUtil.isEmpty(eq.getOption_a()))
			{
				this.options.add(new OptionUIVO("A",eq.getOption_a()));
			}
			if (!StrUtil.isEmpty(eq.getOption_b()))
			{
				this.options.add(new OptionUIVO("B",eq.getOption_b()));
			}
			if (!StrUtil.isEmpty(eq.getOption_c()))
			{
				this.options.add(new OptionUIVO("C",eq.getOption_c()));
			}
			if (!StrUtil.isEmpty(eq.getOption_d()))
			{
				this.options.add(new OptionUIVO("D",eq.getOption_d()));
			}
			if (!StrUtil.isEmpty(eq.getOption_e()))
			{
				this.options.add(new OptionUIVO("E",eq.getOption_e()));
			}
			if (!StrUtil.isEmpty(eq.getOption_f()))
			{
				this.options.add(new OptionUIVO("F",eq.getOption_f()));
			}
			
			

		}

		public List<OptionUIVO> getOptions() {
			return options;
		}

		public String getQuestion() {
			return question;
		}

		public Integer getSerial_numer() {
			return serial_numer;
		}

		public void setOptions(List<OptionUIVO> options) {
			this.options = options;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public void setSerial_numer(Integer serial_numer) {
			this.serial_numer = serial_numer;
		}
	}

	private Integer duration; // 考试时间
	private List<QuestionUIVO> questions = new ArrayList<QuestionUIVO>();// 试题列表

	public PaperUIVO(PaperMainVo paper) {
		super();
		this.duration = paper.getExam_time();
		this.questions = this.getQuestionsByPaper(paper.getId_examquestionvo());
	}

	private List<QuestionUIVO> getQuestionsByPaper(
			List<ExamQuestionVo> questions) {
		List<QuestionUIVO> result = new ArrayList<QuestionUIVO>();
		for (ExamQuestionVo eq : questions) {
			result.add(new QuestionUIVO(eq));
		}
		return result;
	}

	public Integer getDuration() {
		return duration;
	}

	public List<QuestionUIVO> getQuestions() {
		return questions;
	}

	public Integer getTotal_num() {
		return this.getQuestions().size();
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setQuestions(List<QuestionUIVO> questions) {
		this.questions = questions;
	}

}
