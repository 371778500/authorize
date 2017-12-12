package com.yonyou.authorize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.authorize.dao.UserDao;
import com.yonyou.authorize.entity.PUser;

@Service
public class UserService {

	@Autowired
	private UserDao dao;
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	public JSONObject saveUser(PUser user){
		JSONObject obj=new JSONObject();
		try{
			dao.saveUser(user);
			obj.put("flag", "success");
			obj.put("msg", "保存成功");
		}catch(Exception e){
			obj.put("flag", "fail");
			obj.put("msg", e.getMessage());
		}
		return obj;
	}
	
	/**
	 * 查询用户信息
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	public Page<PUser> queryUser(PageRequest pageRequest,String param){
		return dao.queryUser(pageRequest, param);
	}
}
