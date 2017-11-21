package com.yonyou.appbase.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "upesn", name = "UserVO")
@Table(name = "sm_user")
public class UserVO extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3081472213787968785L;

	@Id
	@GeneratedValue(strategy = Stragegy.UUID, moudle = "")
	@Column(name = "pk_sm_user")
	private String pk_sm_user;

	@Column(name = "dept_name")
	private String dept_name;

	@Column(name = "email")
	private String email;

	@Column(name = "memberid")
	private String memberid;

	@Column(name = "name")
	private String name;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "dr")
	private String dr = "0";

	@Column(name = "ts")
	private java.sql.Timestamp ts;

	public UserVO() {
		super();
	}

	public UserVO(UserInfo userInfo) {
		super();
		if (null == userInfo) {
			return;
		}
		this.email = userInfo.getEmail();
		this.dept_name = userInfo.getDeptName();
		this.memberid = userInfo.getMemberId();
		this.name = userInfo.getName();
		this.avatar = userInfo.getAvatar();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.yonyou.iuap.persistence.vo.BaseEntity#getMetaDefinedName()
	 */
	@Override
	public String getMetaDefinedName() {
		return "UserVO";
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.yonyou.iuap.persistence.vo.BaseEntity#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return "upesn";
	}

	/**
	 * @return pk_sm_user
	 */
	public String getPk_sm_user() {
		return pk_sm_user;
	}

	/**
	 * @param pk_sm_user
	 *            要设置的 pk_sm_user
	 */
	public void setPk_sm_user(String pk_sm_user) {
		this.pk_sm_user = pk_sm_user;
	}

	/**
	 * @return dept_name
	 */
	public String getDept_name() {
		return dept_name;
	}

	/**
	 * @param dept_name
	 *            要设置的 dept_name
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            要设置的 email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return memberid
	 */
	public String getMemberid() {
		return memberid;
	}

	/**
	 * @param memberid
	 *            要设置的 memberid
	 */
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            要设置的 avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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
	public java.sql.Timestamp getTs() {
		return ts;
	}

	/**
	 * @param ts
	 *            要设置的 ts
	 */
	public void setTs(java.sql.Timestamp ts) {
		this.ts = ts;
	}

}
