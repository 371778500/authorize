package com.yonyou.authorize.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.authorize.entity.PFun;
import com.yonyou.authorize.entity.PUser;
import com.yonyou.authorize.exception.DIYException;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

/**
 * 功能持久层
 * @author luochp3
 *
 */
@Repository
public class FunDao {
	
	@Autowired
	private BaseDAO dao;
	
	/**
	 * 保存功能
	 * @param user
	 */
	@Transactional
	public void saveFun(PFun fun){
		try{
			dao.save(fun);
		}catch(Exception e){
			throw new DIYException("保存失败");
		}
		
	}
	/**
	 * 查找菜单
	 * @return
	 */
	public List<PFun> queryMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("select * from p_fun where ismenu='1' and dr='0' order by funcode ");
		return dao.queryByClause(PFun.class, sb.toString());
	}
	/**
	 * 根据菜单id查找功能
	 * @return
	 */
	public Page<PFun> queryFun(PageRequest pageRequest,String menuid){
		StringBuffer sb=new StringBuffer();
		sb.append(" select * from p_fun where parentcode="+menuid);
		sb.append(" and dr='0' order by funcode ");
		SQLParameter sqlparam = new SQLParameter();
		return dao.queryPage(sb.toString(), sqlparam, pageRequest, PFun.class);
	}
}
