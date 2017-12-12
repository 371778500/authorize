package com.yonyou.authorize.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the p_role database table.
 * 
 */
@Entity
@Table(name="p_role")
public class PRole extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="roleid")
	private int roleid;

	@Column(name="dr")
	private String dr;

	@Column(name="rolecode")
	private String rolecode;

	@Column(name="rolename")
	private String rolename;
	
	@Column(name="createuser")
	private String createuser;
	
	@Column(name="createtime")
	private String createtime;

	public PRole() {
	}

	public int getRoleid() {
		return this.roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getDr() {
		return this.dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
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