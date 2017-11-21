package com.yonyou.appbase.user.repostitory;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.appbase.user.entity.UserVO;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.userscore.entity.UserAnswerVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Repository
public class UserDAO {

	@Autowired
	private BaseDAO dao;

	public static Logger logger = LoggerFactory.getLogger(UserDAO.class);

	@Transactional
	public UserVO save(UserVO user) {

		if (user.getPk_sm_user() == null) {
			user.setStatus(VOStatus.NEW);
			user.setPk_sm_user(UUID.randomUUID().toString());
			user.setDr("0");// 未删除标识
		} else {
			user.setStatus(VOStatus.UPDATED);
		}
		dao.save(user);
		return user;

	}

	public UserVO getUser(String email) {
		if (StrUtil.isEmpty(email)) {
			logger.error("参数EMAIL为空");
			return null;
		}
		SQLParameter param = new SQLParameter();
		param.addParam(email);

		String sql = " SELECT * FROM sm_user WHERE email = ? ";

		List<UserVO> result = dao.queryByClause(UserVO.class, sql, param);

		if (null == result || result.size() != 1) {
			logger.error("未取得邮件对应人员");
			return null;
		}
		
		return result.get(0);

	}

}
