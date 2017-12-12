package com.yonyou.authorize.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.authorize.entity.PRole;
import com.yonyou.authorize.util.StrUtil;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

@Repository
public class RoleDao {
	
	@Autowired
	private BaseDAO dao;
	
	/**
	 * 查询角色信息
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	public Page<PRole> queryRole(PageRequest pageRequest,String userid,String funid){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from p_role where dr='0' ");
		if(!StrUtil.isEmpty(userid)){
			sb.append(" and roleid in (select roleid from p_user_role where userid=");
			sb.append(userid);
			sb.append(") ");
		}
		if(!StrUtil.isEmpty(funid)){
			sb.append(" and roleid in (select roleid from p_fun_role where funid=");
			sb.append(funid);
			sb.append(") ");
		}
		SQLParameter sqlparam = new SQLParameter();
		return dao.queryPage(sb.toString(), sqlparam, pageRequest, PRole.class);
	}
	

}
