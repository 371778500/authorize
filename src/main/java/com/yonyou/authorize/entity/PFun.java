package com.yonyou.authorize.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * The persistent class for the p_fun database table.
 * 
 */
@Entity
@Table(name="p_fun")
public class PFun extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="funid")
	private int funid;

	@Column(name="dr")
	private String dr;

	@Column(name="funcode")
	private String funcode;

	@Column(name="funname")
	private String funname;

	@Column(name="href")
	private String href;

	@Column(name="iconurl")
	private String iconurl;

	@Column(name="parentcode")
	private String parentcode;

	@Column(name="sort")
	private int sort;
	
	@Column(name="ismenu")
	private String ismenu;

	public PFun() {
	}

	public int getFunid() {
		return this.funid;
	}

	public String getIsmenu() {
		return ismenu;
	}

	public void setIsmenu(String ismenu) {
		this.ismenu = ismenu;
	}

	public void setFunid(int funid) {
		this.funid = funid;
	}

	public String getDr() {
		return this.dr;
	}

	public void setDr(String dr) {
		this.dr = dr;
	}

	public String getFuncode() {
		return this.funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getFunname() {
		return this.funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIconurl() {
		return this.iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getParentcode() {
		return this.parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
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