package com.yonyou.authorize.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;



/**
 * The persistent class for the p_user_role database table.
 * 
 */
@Entity
@Table(name="p_user_role")
public class PUserRole extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private int id;

	@Column(name="dr")
	private String dr;

	@Column(name="roleid")
	private int roleid;

	@Column(name="userid")
	private int userid;

	public PUserRole() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDr() {
		return this.dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public int getRoleid() {
		return this.roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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