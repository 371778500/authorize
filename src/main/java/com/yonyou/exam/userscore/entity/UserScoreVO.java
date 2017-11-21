package com.yonyou.exam.userscore.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.OneToMany;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

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
@Entity(namespace = "upesn", name = "UserScoreVO")
@Table(name = "exam_user_score")
public class UserScoreVO extends BaseEntity implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5987879132172394276L;

	@Column(name = "answeredcount")
	private Integer answeredcount = 0;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "dr")
	private String dr;

	@Column(name = "email")
	private String email;

	@OneToMany(targetEntity = UserAnswerVO.class)
	private List<UserAnswerVO> id_useranswervo = new ArrayList<UserAnswerVO>();

	@Column(name = "name")
	private String name;

	@Column(name = "pk_exam_paper_main")
	private String pk_exam_paper_main;

	@Id
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "")
	@Column(name = "pk_exam_user_score")
	private String pk_exam_user_score;

	@Column(name = "pk_space")
	private String pk_space;

	@Column(name = "test_time")
	private Integer test_time = 0;

	@Column(name = "ts")
	private java.sql.Timestamp ts;

	@Column(name = "user_score")
	private Integer user_score = 0;

	public UserScoreVO() {
		super();
		this.pk_exam_user_score = UUID.randomUUID().toString();
	}

	public UserScoreVO(PaperMainVo paper, Integer test_time, String email,
			Map<String, String> contentmap) {
		super();
		this.pk_exam_user_score = UUID.randomUUID().toString();
		this.test_time = test_time;
		this.answeredcount++;
		this.email = email;
		this.pk_exam_paper_main = paper.getPk_exam_paper_main();

		List<ExamQuestionVo> questionlist = paper.getId_examquestionvo();
		String key = null;
		String answer = null;
		List<UserAnswerVO> answerlist = new ArrayList<UserAnswerVO>();
		for (ExamQuestionVo question : questionlist) {
			key = question.getPk_exam_exam_question();
			if (contentmap.containsKey(key)) {
				answer = contentmap.get(key);
				answerlist.add(new UserAnswerVO(this.getPk_exam_user_score(),
						question, answer));
			} else {
				answerlist.add(new UserAnswerVO(this.getPk_exam_user_score(),
						question));
			}

		}
		this.setId_useranswervo(answerlist);

	}

	public void calcUserScore() {
		this.user_score = 0;
		// 计算用户得分
		if (null == this.id_useranswervo) {
			return;
		}

		for (UserAnswerVO answer : this.id_useranswervo) {
			this.user_score += answer.getScore();
		}
	}

	public UserScoreVO clone() {

		UserScoreVO result = null;
		try {
			result = (UserScoreVO) super.clone();
			List<UserAnswerVO> answers = new ArrayList<UserAnswerVO>();
			for (UserAnswerVO answer : this.id_useranswervo) {
				answers.add((UserAnswerVO) answer.clone());
			}
			result.setId_useranswervo(answers);
		} catch (CloneNotSupportedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return result;
	}

	private Integer getAnswerCountByOptionStatus(Integer status) {

		List<UserAnswerVO> useranswers = this.getId_useranswervo();
		if (null == useranswers || useranswers.size() == 0) {
			return 0;
		}
		Integer result = 0;
		for (UserAnswerVO answer : useranswers) {
			if (answer.getAnswer_status() == status) {
				result++;
			}

		}
		return result;

	}

	public Integer getAnsweredcount() {
		return this.answeredcount;
	}

	/**
	 * @return avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	public String getDr() {
		return dr;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getError_answer_num() {

		return this.getAnswerCountByOptionStatus(0);
	}

	public List<UserAnswerVO> getId_useranswervo() {
		return this.id_useranswervo;
	}

	@Override
	public String getMetaDefinedName() {
		return "UserScoreVO";
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String getNamespace() {
		return "upesn";
	}

	public String getPk_exam_paper_main() {
		return this.pk_exam_paper_main;
	}

	public String getPk_exam_user_score() {
		return this.pk_exam_user_score;
	}

	public String getPk_space() {
		return this.pk_space;
	}

	public Integer getRight_answer_num() {

		return this.getAnswerCountByOptionStatus(1);
	}

	public Integer getTest_time() {
		return this.test_time;
	}

	public java.sql.Timestamp getTs() {
		return ts;
	}

	public Integer getUncompleteAnswer_answer_num() {

		return this.getAnswerCountByOptionStatus(2);
	}

	public Integer getUser_score() {
		return this.user_score;
	}

	public void setAnsweredcount(Integer answeredcount) {
		this.answeredcount = answeredcount;
	}

	/**
	 * @param avatar
	 *            要设置的 avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setDr(String newDr) {
		this.dr = newDr;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId_useranswervo(List<UserAnswerVO> id_useranswervo) {
		this.id_useranswervo = id_useranswervo;
		this.calcUserScore();
	}

	/**
	 * @param name
	 *            要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setPk_exam_paper_main(String pk_exam_paper_main) {
		this.pk_exam_paper_main = pk_exam_paper_main;
	}

	public void setPk_exam_user_score(String pk_exam_user_score) {
		this.pk_exam_user_score = pk_exam_user_score;
	}

	public void setPk_space(String pk_space) {
		this.pk_space = pk_space;
	}

	public void setTest_time(Integer test_time) {
		this.test_time = test_time;
	}

	public void setTs(java.sql.Timestamp newTs) {
		this.ts = newTs;
	}

	public void setUser_score(Integer user_score) {
		this.user_score = user_score;
	}
}