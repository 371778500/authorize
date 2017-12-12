package com.yonyou.authorize.dao;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.authorize.entity.PUser;
import com.yonyou.authorize.exception.DIYException;
import com.yonyou.authorize.util.StrUtil;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * 用户持久层
 * @author luochp3
 *
 */
@Repository
public class UserDao {
	
	@Autowired
	private BaseDAO dao;
	
	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional
	public void saveUser(PUser user){
		try{
			dao.save(user);
		}catch(Exception e){
			throw new DIYException("保存失败");
		}
		
	}
	
	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional
	public void editUser(PUser user){
		try{
			dao.update(user);
		}catch(Exception e){
			throw new DIYException("保存失败");
		}
		
	}
	/**
	 * 查询用户信息
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	public Page<PUser> queryUser(PageRequest pageRequest,String param){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from p_user where dr='0' ");
		if(!StrUtil.isEmpty(param)){
			sb.append(" and (usercode like '%");
			sb.append(param);
			sb.append("%' or name like '%");
			sb.append(param);
			sb.append("%' )");
		}
		SQLParameter sqlparam = new SQLParameter();
		return dao.queryPage(sb.toString(), sqlparam, pageRequest, PUser.class);
	}

}
