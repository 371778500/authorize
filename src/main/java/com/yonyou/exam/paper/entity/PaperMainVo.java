package com.yonyou.exam.paper.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.FK;
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
@Entity(namespace = "upesn", name = "PaperMainVo")
@Table(name = "exam_paper_main")
public class PaperMainVo extends BaseEntity {

	@Column(name = "answeredcount")
	private Integer answeredcount;

	@Column(name = "begin_time")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date begin_time;

	@Column(name = "checkanswer_status")
	private String checkanswer_status = "1";

	@Column(name = "checkscore_status")
	private String checkscore_status = "1";

	@Column(name = "dr")
	private String dr;

	@Column(name = "end_time")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date end_time;

	@Column(name = "exam_time")
	private Integer exam_time;

	@Column(name = "examnum")
	private Integer examnum;

	@FK(name = "fk_id_papermainvo", referenceTableName = "exam_knowledge_type", referencedColumnName = "pk_exam_knowledge_type")
	@Column(name = "fk_id_papermainvo")
	private java.lang.String fk_id_papermainvo;

	@Column(name = "paper_code")
	private String paper_code;

	@Column(name = "paper_name")
	private String paper_name;

	@Column(name = "pass_score")
	private Integer pass_score;

	@Column(name = "pk_exam_knowledge_type")
	private String pk_exam_knowledge_type;

	@Id
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "")
	@Column(name = "pk_exam_paper_main")
	private String pk_exam_paper_main;

	@Column(name = "pk_space")
	private String pk_space;

	@Column(name = "total_score")
	private Integer total_score;

	@Column(name = "ts")
	private java.util.Date ts;

	@Column(name = "upload_date")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date upload_date;

	@Column(name = "uploader")
	private String uploader;

	@Column(name = "using_status")
	private String using_status;

	@OneToMany(targetEntity = ExamQuestionVo.class)
	private List<ExamQuestionVo> id_examquestionvo;

	public List<ExamQuestionVo> getId_examquestionvo() {
		return id_examquestionvo;
	}

	public void setId_examquestionvo(List<ExamQuestionVo> id_examquestionvo) {
		this.id_examquestionvo = id_examquestionvo;
	}

	public Integer getAnsweredcount() {
		return answeredcount;
	}

	public Date getBegin_time() {
		return this.begin_time;
	}

	public String getCheckanswer_status() {
		return this.checkanswer_status;
	}

	public String getCheckscore_status() {
		return this.checkscore_status;
	}

	public String getDr() {
		return dr;
	}

	public Date getEnd_time() {
		return this.end_time;
	}

	public Integer getExam_time() {
		return this.exam_time;
	}

	public Integer getExamnum() {
		return this.examnum;
	}

	/**
	 * 属性 fk的Getter方法.属性名：parentPK 创建日期:2016-11-17
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFk_id_papermainvo() {
		return fk_id_papermainvo;
	}

	@Override
	public String getMetaDefinedName() {
		return "PaperMainVo";
	}

	@Override
	public String getNamespace() {
		return "upesn";
	}

	public String getPaper_code() {
		return this.paper_code;
	}

	public String getPaper_name() {
		return this.paper_name;
	}

	public Integer getPass_score() {
		return this.pass_score;
	}

	public String getPk_exam_knowledge_type() {
		return this.pk_exam_knowledge_type;
	}

	public String getPk_exam_paper_main() {
		return this.pk_exam_paper_main;
	}

	public String getPk_space() {
		return this.pk_space;
	}

	public Integer getTotal_score() {
		return this.total_score;
	}

	/**
	 * 属性 ts的Getter方法.属性名：ts 创建日期:2016-11-17
	 * 
	 * @return java.util.Date
	 */
	public java.util.Date getTs() {
		return ts;
	}

	public Date getUpload_date() {
		return this.upload_date;
	}

	public String getUploader() {
		return this.uploader;
	}

	public String getUsing_status() {
		return this.using_status;
	}

	public void setAnsweredcount(Integer answeredcount) {
		this.answeredcount = answeredcount;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public void setCheckanswer_status(String checkanswer_status) {
		this.checkanswer_status = checkanswer_status;
	}

	public void setCheckscore_status(String checkscore_status) {
		this.checkscore_status = checkscore_status;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public void setExam_time(Integer exam_time) {
		this.exam_time = exam_time;
	}

	public void setExamnum(Integer examnum) {
		this.examnum = examnum;
	}

	/**
	 * 属性fk的Setter方法.属性名：parentPK 创建日期:2016-11-17
	 * 
	 * @param java
	 *            .lang.String
	 */
	public void setFk_id_papermainvo(java.lang.String fk_id_papermainvo) {
		this.fk_id_papermainvo = fk_id_papermainvo;
	}

	public void setPaper_code(String paper_code) {
		this.paper_code = paper_code;
	}

	public void setPaper_name(String paper_name) {
		this.paper_name = paper_name;
	}

	public void setPass_score(Integer pass_score) {
		this.pass_score = pass_score;
	}

	public void setPk_exam_knowledge_type(String pk_exam_knowledge_type) {
		this.pk_exam_knowledge_type = pk_exam_knowledge_type;
	}

	public void setPk_exam_paper_main(String pk_exam_paper_main) {
		this.pk_exam_paper_main = pk_exam_paper_main;
	}

	public void setPk_space(String pk_space) {
		this.pk_space = pk_space;
	}

	public void setTotal_score(Integer total_score) {
		this.total_score = total_score;
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

	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public void setUsing_status(String using_status) {
		this.using_status = using_status;
	}
}
