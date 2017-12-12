package com.yonyou.authorize.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.authorize.entity.PRole;
import com.yonyou.authorize.entity.PUser;
import com.yonyou.authorize.service.RoleService;
import com.yonyou.authorize.util.StrUtil;

@Controller
@RequestMapping(value = "/user")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	/**
	 * 获取角色信息
	 * @param pageNumber
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryrole", method = RequestMethod.GET)
	public @ResponseBody Object queryRole(@RequestParam(value = "pageIndex", defaultValue = "1") int pageNumber,
    		@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
    		HttpServletRequest request, HttpServletResponse response){
		JSONObject obj=new JSONObject();
		
		String userid=StrUtil.nullToString(request.getParameter("userid"));
		String funid=StrUtil.nullToString(request.getParameter("funid"));
		PageRequest pageRequest=buildPageRequest(pageNumber,pageSize,Direction.ASC,"rolecode");
		Page<PRole> role=service.queryRole(pageRequest, userid, funid);
		
		obj.put("flag", "success");
		obj.put("data", role);
		return obj;
	}
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize,Direction dir,String sortType) {
		Sort sort = null;
		sort = new Sort(dir,sortType);
		return new PageRequest(pageNumber-1, pagzSize, sort);
	}
}
