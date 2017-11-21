package com.yonyou.exam.paper.entity;

import java.util.List;
	

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Column;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.GeneratedValue;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Id;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.OneToMany;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Stragegy;
import com.yonyou.iuap.persistence.jdbc.framework.annotation.Table;
import com.yonyou.iuap.persistence.vo.BaseEntity;


/**
 * <b> 此处简要描述此类功能 </b>
 * <p>
 *   此处添加类的描述信息
 * </p>
 *  创建日期:2016-11-17
 * @author 
 * @version 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(namespace = "upesn",name = "KnowledgeTypeVo")
@Table(name = "exam_knowledge_type")
public class KnowledgeTypeVo extends BaseEntity{
	@Id
	@GeneratedValue(strategy=Stragegy.UUID,moudle="")
	@Column(name = "pk_exam_knowledge_type")
	private String pk_exam_knowledge_type;

	@Column(name = "pk_space")
	private String pk_space;

	@Column(name = "knowlege_type_code")
	private String knowlege_type_code;

	@Column(name = "knowlege_type_name")
	private String knowlege_type_name;

	@Column(name = "pk_knowledge_type_parentid")
	private String pk_knowledge_type_parentid;

	private String pk_knowledge_type_parentid_name;  //参照需要显示的名字
	
	@OneToMany(targetEntity = PaperMainVo.class)
	private List<PaperMainVo> id_papermainvo;

	@Column(name = "dr")
    private java.lang.Integer dr = 0 ;
      
    @Column(name = "ts")
    private java.sql.Timestamp ts ;


	public String getPk_exam_knowledge_type() {
		return this.pk_exam_knowledge_type;
	}

	public void setPk_exam_knowledge_type(String pk_exam_knowledge_type) {
		this.pk_exam_knowledge_type = pk_exam_knowledge_type;
	}


	public String getPk_space() {
		return this.pk_space;
	}

	public void setPk_space(String pk_space) {
		this.pk_space = pk_space;
	}


	public String getKnowlege_type_code() {
		return this.knowlege_type_code;
	}

	public void setKnowlege_type_code(String knowlege_type_code) {
		this.knowlege_type_code = knowlege_type_code;
	}


	public String getKnowlege_type_name() {
		return this.knowlege_type_name;
	}

	public void setKnowlege_type_name(String knowlege_type_name) {
		this.knowlege_type_name = knowlege_type_name;
	}


	public String getPk_knowledge_type_parentid() {
		return this.pk_knowledge_type_parentid;
	}

	public void setPk_knowledge_type_parentid(String pk_knowledge_type_parentid) {
		this.pk_knowledge_type_parentid = pk_knowledge_type_parentid;
	}


	public List<PaperMainVo> getId_papermainvo() {
		return this.id_papermainvo;
	}

	public void setId_papermainvo(List<PaperMainVo> id_papermainvo) {
		this.id_papermainvo = id_papermainvo;
	}

	public java.lang.Integer getDr () {
		return dr;
	}
	
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	
	
	public java.sql.Timestamp getTs () {
		return ts;
	}
	
	public void setTs (java.sql.Timestamp newTs ) {
	 	this.ts = newTs;
	} 
	
	@Override
    public String getMetaDefinedName() {
        return "KnowledgeTypeVo";
    }

    @Override
    public String getNamespace() {
        return "upesn";
    }
}