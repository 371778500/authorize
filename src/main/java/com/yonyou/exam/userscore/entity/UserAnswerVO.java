package com.yonyou.exam.userscore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import java.util.List;
import java.util.UUID;

/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 * 此处添加类的描述信息
 * </p>
 * 创建日期:2016-11-17
 * 
 * @author
 * @version
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "upesn", name = "UserAnswerVO")
@Table(name = "exam_user_answer")
public class UserAnswerVO extends BaseEntity implements Cloneable {

	// @FK(name = "fk_id_useranswervo", referenceTableName = "exam_user_score",
	// referencedColumnName = "pk_exam_user_score")
	// @Column(name = "fk_id_useranswervo")
	// private java.lang.String fk_id_useranswervo;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1396857619398619922L;

	@Id
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "")
	@Column(name = "pk_exam_user_answer")
	private String pk_exam_user_answer;

	@Column(name = "pk_exam_user_score")
	private String pk_exam_user_score;

	@Column(name = "pk_exam_exam_question")
	private String pk_exam_exam_question;

	@Column(name = "answer")
	private String answer;

	@Column(name = "score")
	private Integer score = 0;

	@Column(name = "dr")
	private String dr;

	@Column(name = "ts")
	private java.util.Date ts;

	/**
	 * 属性 fk的Getter方法.属性名：parentPK 创建日期:2016-11-17
	 * 
	 * @return java.lang.String
	 */
	// public java.lang.String getFk_id_useranswervo() {
	// return fk_id_useranswervo;
	// }

	/**
	 * 属性fk的Setter方法.属性名：parentPK 创建日期:2016-11-17
	 * 
	 * @param newFk
	 *            java.lang.String
	 */
	// public void setFk_id_useranswervo(java.lang.String fk_id_useranswervo) {
	// this.fk_id_useranswervo = fk_id_useranswervo;
	// }

	public String getPk_exam_user_answer() {
		return this.pk_exam_user_answer;
	}

	public void setPk_exam_user_answer(String pk_exam_user_answer) {
		this.pk_exam_user_answer = pk_exam_user_answer;
	}

	public String getPk_exam_user_score() {
		return this.pk_exam_user_score;
	}

	public void setPk_exam_user_score(String pk_exam_user_score) {
		this.pk_exam_user_score = pk_exam_user_score;
	}

	public String getPk_exam_exam_question() {
		return this.pk_exam_exam_question;
	}

	public void setPk_exam_exam_question(String pk_exam_exam_question) {
		this.pk_exam_exam_question = pk_exam_exam_question;
	}

	public Integer getAnswer_status() {
		if (StrUtil.isEmpty(this.getAnswer())) {
			// 未答
			return 2;
		}
		if (!StrUtil.isEmpty(this.getAnswer()))
			if (this.score > 0) {
				// 部分对和全对
				return 1;
			}

		if (!StrUtil.isEmpty(this.getAnswer()))
			if (this.score == 0) {
				// 错误
				return 0;
			}

		return -1;

	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 属性 dr的Getter方法.属性名：dr 创建日期:2016-11-17
	 * 
	 * @return java.lang.Integer
	 */
	public String getDr() {
		return dr;
	}

	/**
	 * 属性dr的Setter方法.属性名：dr 创建日期:2016-11-17
	 * 
	 * @param newDr
	 *            java.lang.Integer
	 */
	public void setDr(String newDr) {
		this.dr = newDr;
	}

	/**
	 * 属性 ts的Getter方法.属性名：ts 创建日期:2016-11-17
	 * 
	 * @return java.util.Date
	 */
	public java.util.Date getTs() {
		return ts;
	}

	/**
	 * 属性ts的Setter方法.属性名：ts 创建日期:2016-11-17
	 * 
	 * @param newTs
	 *            java.util.Date
	 */
	public void setTs(java.util.Date newTs) {
		this.ts = newTs;
	}

	@Override
	public String getMetaDefinedName() {
		return "UserAnswerVO";
	}

	@Override
	public String getNamespace() {
		return "upesn";
	}

	public UserAnswerVO() {
		super();
		this.pk_exam_user_answer = UUID.randomUUID().toString();
	}

	public UserAnswerVO(String pk_exam_user_score, ExamQuestionVo question,
			String answer) {
		super();
		this.pk_exam_user_answer = UUID.randomUUID().toString();
		this.pk_exam_user_score = pk_exam_user_score;
		this.answer = answer;
		this.pk_exam_exam_question = question.getPk_exam_exam_question();
		this.score = question.calcScore(answer);

	}

	public UserAnswerVO(String pk_exam_user_score, ExamQuestionVo question) {
		super();
		this.pk_exam_user_answer = UUID.randomUUID().toString();
		this.pk_exam_user_score = pk_exam_user_score;
		this.answer = null;
		this.pk_exam_exam_question = question.getPk_exam_exam_question();
		this.score = 0;

	}

	public UserAnswerVO clone() {

		UserAnswerVO result = null;
		try {
			result = (UserAnswerVO) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
}