package com.yonyou.upesn.entity.course;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.upesn.common.enums.TrueOrFalseEnum;

/**
 * The persistent class for the course database table.
 * 
 */
@Entity
@Table(name="course")
public class Course extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程id
	 */
	@Id
	@Column(name="PK_COURSE")
	private String pkCourse;
	/**
	 * 课程播放总数
	 */
	@Column(name="COURSE_BROADCAST_NUMBER")
	private int courseBroadcastNumber;
	/**
	 * 课程点击总数
	 */
	@Column(name="COURSE_CLICK_NUMBER")
	private int courseClickNumber;
	/**
	 * 课程编码
	 */
	@Column(name="COURSE_CODE")
	private String courseCode;
	/**
	 * 课程时长
	 */
	@Column(name="COURSE_DURAION")
	private String courseDuraion;
	/**
	 * 课程图标url
	 */
	@Column(name="COURSE_ICON_URL")
	private String courseIconUrl;
	/**
	 * 课程介绍
	 */
	@Column(name="COURSE_INTRODUCTION")
	private String courseIntroduction;
	/**
	 * 课程讲师
	 */
	@Column(name="COURSE_LECTURER")
	private String courseLecturer;
	/**
	 * 课程点赞总数
	 */
	@Column(name="COURSE_LIKE_NUMBER")
	private int courseLikeNumber;
	/**
	 * 课程名称
	 */
	@Column(name="COURSE_NAME")
	private String courseName;
	/**
	 * 课程发布人
	 */
	@Column(name="COURSE_PUBLISHER")
	private String coursePublisher;
	/**
	 * 课程章节
	 */
	@Column(name="COURSE_SECTION")
	private String courseSection;
	/**
	 * 课程url
	 */
	@Column(name="COURSE_URL")
	private String courseUrl;
	/**
	 * 课程类型主键
	 */
	@Column(name="PK_COURSE_TYPE")
	private String pkCourseType;
	/**
	 * 是否启用  1：已经启用 0:未启用
	 */
	@Column(name="IS_USE")
	private String isuse;

	@Column(name="DR")
	private String dr;
	/**
	 * 课程发布时间
	 */
	@Column(name="COURSE_PUBLISH_TIME")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
//	@Temporal(TemporalType.TIMESTAMP)
	private Date coursePublishTime;
	
	@Column(name = "ts")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date ts;
	/**
	 * 章节内容
	 */
	@Column(name="chapter_url")
	private String chapterUrl;
	
	public String getChapterUrl() {
		return chapterUrl;
	}

	public void setChapterUrl(String chapterUrl) {
		this.chapterUrl = chapterUrl;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public void setCoursePublishTime(Date coursePublishTime) {
		this.coursePublishTime = coursePublishTime;
	}

	public Date getCoursePublishTime() {
		return coursePublishTime;
	}

//	public void setCoursePublishTime(Date coursePublishTime) {
//		 SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
//	  
//	            try {
//					this.coursePublishTime = f.parse(coursePublishTime.toString());
//				} catch (java.text.ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}  
//	      
//	}

	public String getDr() {
		return dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public Course() {
	}

	public String getPkCourse() {
		return this.pkCourse;
	}

	public void setPkCourse(String pkCourse) {
		this.pkCourse = pkCourse;
	}

	public int getCourseBroadcastNumber() {
		return this.courseBroadcastNumber;
	}

	public void setCourseBroadcastNumber(int courseBroadcastNumber) {
		this.courseBroadcastNumber = courseBroadcastNumber;
	}

	public int getCourseClickNumber() {
		return this.courseClickNumber;
	}

	public void setCourseClickNumber(int courseClickNumber) {
		this.courseClickNumber = courseClickNumber;
	}

	public String getCourseCode() {
		return this.courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseDuraion() {
		return this.courseDuraion;
	}

	public void setCourseDuraion(String courseDuraion) {
		this.courseDuraion = courseDuraion;
	}

	public String getCourseIconUrl() {
		return this.courseIconUrl;
	}

	public void setCourseIconUrl(String courseIconUrl) {
		this.courseIconUrl = courseIconUrl;
	}

	public String getCourseIntroduction() {
		return this.courseIntroduction;
	}

	public void setCourseIntroduction(String courseIntroduction) {
		this.courseIntroduction = courseIntroduction;
	}

	public String getCourseLecturer() {
		return this.courseLecturer;
	}

	public void setCourseLecturer(String courseLecturer) {
		this.courseLecturer = courseLecturer;
	}

	public int getCourseLikeNumber() {
		return this.courseLikeNumber;
	}

	public void setCourseLikeNumber(int courseLikeNumber) {
		this.courseLikeNumber = courseLikeNumber;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCoursePublisher() {
		return this.coursePublisher;
	}

	public void setCoursePublisher(String coursePublisher) {
		this.coursePublisher = coursePublisher;
	}

	public String getCourseSection() {
		return this.courseSection;
	}

	public void setCourseSection(String courseSection) {
		this.courseSection = courseSection;
	}

	public String getCourseUrl() {
		return this.courseUrl;
	}

	public void setCourseUrl(String courseUrl) {
		this.courseUrl = courseUrl;
	}

	public String getIsuse() {
		
		return this.isuse;
	}

	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}

	public String getPkCourseType() {
		return this.pkCourseType;
	}

	public void setPkCourseType(String pkCourseType) {
		this.pkCourseType = pkCourseType;
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