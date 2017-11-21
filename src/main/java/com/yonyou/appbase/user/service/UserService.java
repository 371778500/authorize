package com.yonyou.appbase.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.appbase.user.entity.DefaultUserInfo;
import com.yonyou.appbase.user.entity.ThreadUserInfo;
import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.appbase.user.entity.UserVO;
import com.yonyou.appbase.user.repostitory.UserDAO;

@Service
public class UserService {

	@Autowired
	UserDAO userdao;
	public static Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserInfo getUserinfo()

	{
		ThreadUserInfo threadUserInfo = new ThreadUserInfo();
		UserInfo result = threadUserInfo.getCurrentUserInfo();
		if (null == result) {
			logger.error("未取得用户信息，使用默认用户信息");
			result = new DefaultUserInfo();
		}
		return result;
	}

	public UserVO save(UserVO user) {
		if (null == user) {
			return null;
		}
		String email = user.getEmail();
		UserVO olduser = this.getUser(email);
		if (olduser != null) {
			user.setPk_sm_user(olduser.getPk_sm_user());
		}
		return userdao.save(user);

	}

	public UserVO getUser(String email) {
		return userdao.getUser(email);
	}

}
