package com.yonyou.exam.paper.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.yonyou.appbase.util.StrUtil;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
 * 选择题知识明细实体类 创建时间：2017-09-24
 * 
 * @author Administrator
 *
 */
@Entity(namespace = "upesn", name = "ExamQuestionVo")
@Table(name = "exam_exam_question")
public class ExamQuestionVo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@FK(name = "fk_id_examquestionvo", referenceTableName = "exam_paper_main", referencedColumnName = "pk_exam_paper_main")
	@Column(name = "fk_id_examquestionvo")
	private String fk_id_examquestionvo;

	/**
	 * 选择题类型主键
	 */
	@Id
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "")
	@Column(name = "pk_exam_exam_question")
	private String pk_exam_exam_question;

	/**
	 * 试卷管理主键(外键)
	 */
	@Column(name = "pk_exam_paper_main")
	private String pk_exam_paper_main;

	/**
	 * 名称空间
	 */
	@Column(name = "pk_space")
	private String pk_space;

	/**
	 * 选择题题型
	 */
	@Column(name = "question_type")
	private String question_type;

	/**
	 * 选择题题号
	 */
	@Column(name = "question_num")
	private Integer question_num;

	/**
	 * 选择题题干
	 */
	@Column(name = "question_title")
	private String question_title;

	/**
	 * 选项A
	 */
	@Column(name = "option_a")
	private String option_a;

	/**
	 * 选项B
	 */
	@Column(name = "option_b")
	private String option_b;

	/**
	 * 选项C
	 */
	@Column(name = "option_c")
	private String option_c;

	/**
	 * 选项D
	 */
	@Column(name = "option_d")
	private String option_d;

	/**
	 * 选项E
	 */
	@Column(name = "option_e")
	private String option_e;

	/**
	 * 选项F
	 */
	@Column(name = "option_f")
	private String option_f;

	/**
	 * 分数
	 */
	@Column(name = "score")
	private Integer score;

	/**
	 * 正确答案
	 */
	@Column(name = "right_answer")
	private String right_answer;

	/**
	 * 答案解析
	 */
	@Column(name = "right_answer_analysis")
	private String right_answer_analysis;

	/**
	 * 删除标志
	 */
	@Column(name = "dr")
	private String dr;

	/**
	 * 时间戳
	 */
	@Column(name = "ts")
	private Date ts;

	/**
	 * 选项类型 0-单项选择 1-多项选择
	 */
	@Column(name = "optiontype")
	private Integer optiontype;

	public Integer getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(Integer optiontype) {
		this.optiontype = optiontype;
	}

	/**
	 * @return fk_id_examquestionvo
	 */
	public String getFk_id_examquestionvo() {
		return fk_id_examquestionvo;
	}

	/**
	 * @param fk_id_examquestionvo
	 *            要设置的 fk_id_examquestionvo
	 */
	public void setFk_id_examquestionvo(String fk_id_examquestionvo) {
		this.fk_id_examquestionvo = fk_id_examquestionvo;
	}

	/**
	 * @return pk_exam_exam_question
	 */
	public String getPk_exam_exam_question() {
		return pk_exam_exam_question;
	}

	/**
	 * @param pk_exam_exam_question
	 *            要设置的 pk_exam_exam_question
	 */
	public void setPk_exam_exam_question(String pk_exam_exam_question) {
		this.pk_exam_exam_question = pk_exam_exam_question;
	}

	/**
	 * @return pk_exam_paper_main
	 */
	public String getPk_exam_paper_main() {
		return pk_exam_paper_main;
	}

	/**
	 * @param pk_exam_paper_main
	 *            要设置的 pk_exam_paper_main
	 */
	public void setPk_exam_paper_main(String pk_exam_paper_main) {
		this.pk_exam_paper_main = pk_exam_paper_main;
	}

	/**
	 * @return pk_space
	 */
	public String getPk_space() {
		return pk_space;
	}

	/**
	 * @param pk_space
	 *            要设置的 pk_space
	 */
	public void setPk_space(String pk_space) {
		this.pk_space = pk_space;
	}

	/**
	 * @return question_type
	 */
	public String getQuestion_type() {
		return question_type;
	}

	/**
	 * @param question_type
	 *            要设置的 question_type
	 */
	public void setQuestion_type(String question_type) {
		this.question_type = question_type;
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
	 * @return option_a
	 */
	public String getOption_a() {
		return option_a;
	}

	/**
	 * @param option_a
	 *            要设置的 option_a
	 */
	public void setOption_a(String option_a) {
		this.option_a = option_a;
	}

	/**
	 * @return option_b
	 */
	public String getOption_b() {
		return option_b;
	}

	/**
	 * @param option_b
	 *            要设置的 option_b
	 */
	public void setOption_b(String option_b) {
		this.option_b = option_b;
	}

	/**
	 * @return option_c
	 */
	public String getOption_c() {
		return option_c;
	}

	/**
	 * @param option_c
	 *            要设置的 option_c
	 */
	public void setOption_c(String option_c) {
		this.option_c = option_c;
	}

	/**
	 * @return option_d
	 */
	public String getOption_d() {
		return option_d;
	}

	/**
	 * @param option_d
	 *            要设置的 option_d
	 */
	public void setOption_d(String option_d) {
		this.option_d = option_d;
	}

	/**
	 * @return option_e
	 */
	public String getOption_e() {
		return option_e;
	}

	/**
	 * @param option_e
	 *            要设置的 option_e
	 */
	public void setOption_e(String option_e) {
		this.option_e = option_e;
	}

	/**
	 * @return option_f
	 */
	public String getOption_f() {
		return option_f;
	}

	/**
	 * @param option_f
	 *            要设置的 option_f
	 */
	public void setOption_f(String option_f) {
		this.option_f = option_f;
	}

	/**
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            要设置的 score
	 */
	public void setScore(Integer score) {
		this.score = score;
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
	 * @return dr
	 */
	public String getDr() {
		return dr;
	}

	/**
	 * @param dr
	 *            要设置的 dr
	 */
	public void setDr(String dr) {
		this.dr = dr;
	}

	/**
	 * @return ts
	 */
	public Date getTs() {
		return ts;
	}

	/**
	 * @param ts
	 *            要设置的 ts
	 */
	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Integer calcScore(String content) {
		if (StrUtil.isEmpty(content)) {
			// 没答案,得0分
			return 0;
		}

		Set<String> rightanswerset = this.getAnswerSet(this.getRight_answer());
		Set<String> answerset = this.getAnswerSet(content);

		if (this.optiontype.equals("0"))
		{
			return this.calcSingleOptionScore(rightanswerset, answerset);
		}
		return this.calcMultiOptionSocre(rightanswerset, answerset);

	}

	private Integer calcSingleOptionScore(Set<String> rightanswerset,
			Set<String> answerset) {
		rightanswerset.removeAll(answerset);
		if (rightanswerset.size() != 0) {
			// 错误，得0分
			return 0;
		}
		return this.getScore();
	}

	private Integer calcMultiOptionSocre(Set<String> rightanswerset,
			Set<String> answerset) {
			
		boolean isExistsFailAnswer = false;
		int successanswercount = 0;
		for(String answer : answerset)
		{
			if (!rightanswerset.contains(answer))
			{
				//出现正确答案中没有的内容,则有错误答案
				isExistsFailAnswer = true;
				break;
			}
			successanswercount++;
		}
		
		if (isExistsFailAnswer)
		{
			//有错误答案，得0分
			return 0;
		}
		
		//如果正确答案数量=正确集合的数量，全对，得满分
		if ( rightanswerset.size() == successanswercount)
		{
			return this.getScore();
		}
		
		//部分正确，得一半分数
		return  (new Double(Math.floor(this.score / 2))).intValue();
		

	}

	private Set<String> getAnswerSet(String answerstr) {
		Set<String> result = new HashSet<String>();
		if (StrUtil.isEmpty(answerstr)) {
			return result;
		}
		String[] answerlist = answerstr.split(",");
		for (String answer : answerlist) {
			result.add(answer);
		}
		return result;

	}

}
