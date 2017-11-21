package com.yonyou.upesn.entity.coursetype;

import java.util.ArrayList;
import java.util.List;

public class CoursetypeVO {
	private String id;
	private String name;
	private List<CoursetypeVO> sublist;
	public CoursetypeVO(){
		sublist=new ArrayList<CoursetypeVO>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<CoursetypeVO> getSublist() {
		return sublist;
	}
	public void setSublist(List<CoursetypeVO> sublist) {
		this.sublist = sublist;
	}
	public void putSubList(CoursetypeVO e){
		if(e.getId()!=null){
			this.sublist.add(e);
		}
	}

}
