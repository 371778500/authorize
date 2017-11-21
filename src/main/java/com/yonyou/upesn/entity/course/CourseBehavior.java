package com.yonyou.upesn.entity.course;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the course_behavior database table.
 * 
 */
@Entity
@Table(name="course_behavior")
public class CourseBehavior extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 课程行为id
	 */
	@Id
	@Column(name="PK_COURSE_BEHAVIOR")
	private String pkCourseBehavior;
	/**
	 * 行为时长
	 */
	@Column(name="COURSE_BEHAVIOR_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date courseBehaviorTime;
	/**
	 * 课程行为类型（0:课程进入1:课程播放2:课程点赞）
	 */
	@Column(name="COURSE_BEHAVIOR_TYPE")
	private String courseBehaviorType;
	/**
	 * 课程id
	 */
	@Column(name="PK_COURSE")
	private String pkCourse;
	/**
	 * 用户id
	 */
	@Column(name="PK_USER")
	private String pkUser;

	/**
	 * dr
	 */
	@Column(name="DR")
	private String dr;
	
	@Column(name = "ts")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date ts;
	
	public String getDR() {
		return dr;
	}

	public void setDR(String dR) {
		dr = dR;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public CourseBehavior() {
	}

	public String getPkCourseBehavior() {
		return this.pkCourseBehavior;
	}

	public void setPkCourseBehavior(String pkCourseBehavior) {
		this.pkCourseBehavior = pkCourseBehavior;
	}

	public Date getCourseBehaviorTime() {
		return this.courseBehaviorTime;
	}

	public void setCourseBehaviorTime(Date courseBehaviorTime) {
		this.courseBehaviorTime = courseBehaviorTime;
	}

	public String getCourseBehaviorType() {
		return this.courseBehaviorType;
	}

	public void setCourseBehaviorType(String courseBehaviorType) {
		this.courseBehaviorType = courseBehaviorType;
	}

	public String getPkCourse() {
		return this.pkCourse;
	}

	public void setPkCourse(String pkCourse) {
		this.pkCourse = pkCourse;
	}

	public String getPkUser() {
		return this.pkUser;
	}

	public void setPkUser(String pkUser) {
		this.pkUser = pkUser;
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