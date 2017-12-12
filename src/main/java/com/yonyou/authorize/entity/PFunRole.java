package com.yonyou.authorize.entity;


import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the p_fun_role database table.
 * 
 */
@Entity
@Table(name="p_fun_role")
public class PFunRole extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="dr")
	private String dr;

	@Column(name="funid")
	private int funid;

	@Column(name="roleid")
	private int roleid;

	public PFunRole() {
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

	public int getFunid() {
		return this.funid;
	}

	public void setFunid(int funid) {
		this.funid = funid;
	}

	public int getRoleid() {
		return this.roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
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