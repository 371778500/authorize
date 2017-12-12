package com.yonyou.authorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.authorize.dao.RoleDao;
import com.yonyou.authorize.entity.PRole;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao dao;

	/**
	 * 查询角色信息
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	public Page<PRole> queryRole(PageRequest pageRequest,String userid,String funid){
		return dao.queryRole(pageRequest, userid, funid);
	}
}
