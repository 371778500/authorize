package com.yonyou.authorize.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;



/**
 * The persistent class for the p_user database table.
 * 
 */
@Entity
@Table(name="p_user")
public class PUser extends BaseEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="userid")
	private int userid;

	@Column(name="dr")
	private String dr;

	@Column(name="enable")
	private String enable;

	@Column(name="name")
	private String name;

	@Column(name="password")
	private String password;

	@Column(name="usercode")
	private String usercode;

	@Column(name="username")
	private String username;

	public PUser() {
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getDr() {
		return this.dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@Override
    public String getMetaDefinedName() {
        return "authorize";
    }

    @Override
    public String getNamespace() {
        return "metadata";
    }

}