package com.yonyou.upesn.entity.comment;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the course_comment database table.
 * 
 */
@Entity
@Table(name="course_comment")
public class CourseComment extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;

	@Column(name="content")
	private String content;

	@Column(name="course_id")
	private String courseId;

	
	@Column(name="create_time")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date createTime;
	
	@Column(name="ts")
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date ts;

	@Column(name="dr")
	private String dr;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_pic")
	private String userPic;

	public CourseComment() {
	}

	
	public Date getTs() {
		return ts;
	}


	public void setTs(Date ts) {
		this.ts = ts;
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDr() {
		return this.dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPic() {
		return this.userPic;
	}

	public void setUserPic(String userPic) {
		this.userPic = userPic;
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