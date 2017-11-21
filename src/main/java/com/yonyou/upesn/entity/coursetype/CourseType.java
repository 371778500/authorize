package com.yonyou.upesn.entity.coursetype;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the course_type database table.
 * 
 */
@Entity
@Table(name="course_type")
public class CourseType extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 课程类型主键
	 */
	@Id
	@Column(name="PK_COURSE_TYPE")
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	private String pkCourseType;

	/**
	 * 课程分类编码
	 */
	@Column(name="COURSE_TYPE_CODE")
	private String courseTypeCode;

	/**
	 * 课程类型名称
	 */
	@Column(name="COURSE_TYPE_NAME")
	private String courseTypeName;

	/**
	 * 课程类型父id,一级分类父id默认为 root
	 */
	@Column(name="PK_COURSE_TYPE_PARENTID")
	private String pkCourseTypeParentid;
	
	@Column(name = "ts")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date ts;

	@Column(name="DR")
	private String dr;
	
	public String getDr() {
		return dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public CourseType() {
	}

	public String getCourseTypeCode() {
		return courseTypeCode;
	}

	public void setCourseTypeCode(String courseTypeCode) {
		this.courseTypeCode = courseTypeCode;
	}

	public String getPkCourseType() {
		return this.pkCourseType;
	}

	public void setPkCourseType(String pkCourseType) {
		this.pkCourseType = pkCourseType;
	}

	public String getCourseTypeName() {
		return this.courseTypeName;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
	}

	public String getPkCourseTypeParentid() {
		return this.pkCourseTypeParentid;
	}

	public void setPkCourseTypeParentid(String pkCourseTypeParentid) {
		this.pkCourseTypeParentid = pkCourseTypeParentid;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}
	
    public String getMetaDefinedName(){
		return "upesn";
	}
	public String getNamespace(){
		return "com.yonyou.upesn.metadata";
	}
	
}
