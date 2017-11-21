package com.yonyou.appbase.user.entity;



public class ThreadUserInfo {

	private static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<UserInfo>();
	
	public static UserInfo getCurrentUserInfo(){
		return threadLocal.get();
	}
	
	public static void setCurrentUserInfo(UserInfo info){
		threadLocal.set(info);
	}
	


}
