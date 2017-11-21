package com.yonyou.upesn.entity.course;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the course_visible_type database table.
 * 
 */
@Entity
@Table(name="course_visible_type")
public class CourseVisibleType extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程可见范围表主键
	 */
	@Id
	@Column(name="PK_COURSE_VISIBLE_TYPE")
	private String pkCourseVisibleType;

	/**
	 * 课程可见id
	 */
	@Column(name="PK_COURSE_VISIBLE_ID")
	private String pkCourseVisibleId;
	
	/**
	 * 课程可见类型
	 */
	@Column(name="COURSE_VISIBLE_TYPE")
	private String CourseVisibleType;
	
	/**
	 * 课程id
	 */
	@Column(name="PK_COURSE")
	private String pkCourse;
	
	/**
	 * dr
	 */
	@Column(name="DR")
	private String DR;
	
	@Column(name = "TS")
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date ts;
	
	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public CourseVisibleType() {
	}

	public String getPkCourseVisibleType() {
		return this.pkCourseVisibleType;
	}

	public void setPkCourseVisibleType(String pkCourseVisibleType) {
		this.pkCourseVisibleType = pkCourseVisibleType;
	}

	public String getPkCourseVisibleId() {
		return this.pkCourseVisibleId;
	}

	public void setPkCourseVisibleId(String pkCourseVisibleId) {
		this.pkCourseVisibleId = pkCourseVisibleId;
	}

	public String getCourseVisibleType() {
		return CourseVisibleType;
	}

	public void setCourseVisibleType(String courseVisibleType) {
		CourseVisibleType = courseVisibleType;
	}

	public String getPkCourse() {
		return pkCourse;
	}

	public void setPkCourse(String pkCourse) {
		this.pkCourse = pkCourse;
	}

	public String getDR() {
		return DR;
	}

	public void setDR(String dR) {
		DR = dR;
	}

	@Override
    public String getMetaDefinedName() {
        return "upesn";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }
}