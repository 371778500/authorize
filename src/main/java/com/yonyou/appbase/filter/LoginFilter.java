package com.yonyou.appbase.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.appbase.user.entity.ThreadUserInfo;
import com.yonyou.appbase.user.entity.Token;
import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.appbase.user.entity.UserVO;
import com.yonyou.appbase.user.service.UserService;
import com.yonyou.appbase.util.HttpClientUtils;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.upesn.service.IAuthAppSuites;
import com.yonyou.upesn.service.impl.AuthAppSuitesImp;

/**
 * Servlet Filter implementation class LoginFilter
 */
@Component
public class LoginFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginFilter.class);

	private static final String ACCESS_TOKEN = "exam_access_token";

	private static final String CODE_PREFIX = "user_code_prefix";

	private CacheManager cache;

	private List<String> excludeUrlsList;

	private IAuthAppSuites authAppSuites;

	private static final String EXCLUDEURLS = "excludeUrls";

	private String upesnBaseUrl;

	private String suiteKey;

	private String upesn_appid;

	private String upesn_secret;



	/**
	 * Default constructor.
	 */
	public LoginFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if(canIgnore(httpRequest)){
			chain.doFilter(request, response);
			return;
		}
		String code = request.getParameter("code");
		Cookie[] cookies = null;
		if(StringUtils.isBlank(code)){
			try {
				cookies = httpRequest.getCookies();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(cookies!=null&&cookies.length>-1){
				for (Cookie cookie : cookies) {
					//设置cookie
					if("code".equals(cookie.getName())){
						code = cookie.getValue();
						break;
					}
				}	
			}
		}
		
		if(StringUtils.isNotBlank(code)){
			//设置cookie
			Cookie cookie = new Cookie("code", code);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			httpResponse.addCookie(cookie);
		}
		
		String userInfoString = cache.get(CODE_PREFIX+code);
		if(StringUtils.isBlank(userInfoString)){
			if(StringUtils.isBlank(code)){
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().println("请从友空间进入");
				return;
			}
			//执行友空间免登操作
			this.login(httpRequest,httpResponse);
		}else{
			JSONObject json = (JSONObject) JSONObject.parse(userInfoString);
			String str=URLEncoder.encode(json.getString("name"),"UTF-8");
			Cookie name = new Cookie("name",str);
			name.setPath("/");
			name.setMaxAge(-1);
			Cookie pic = new Cookie("pic", json.getString("avatar"));
			pic.setPath("/");
			pic.setMaxAge(-1);
			Cookie userid = new Cookie("userid", json.getString("memberId"));
			userid.setPath("/");
			userid.setMaxAge(-1);
			
			httpResponse.addCookie(name);
			httpResponse.addCookie(pic);
			httpResponse.addCookie(userid);
			ThreadUserInfo.setCurrentUserInfo(JSONObject.parseObject(userInfoString,UserInfo.class));
			this.saveUserInfo(JSONObject.parseObject(userInfoString,UserInfo.class));
		}
		chain.doFilter(request, response);
			
	}

	/**
	 * 判断路径是否可放过
	 * 
	 * @param request
	 * @return
	 */
	private boolean canIgnore(HttpServletRequest request) {
		String url = request.getServletPath();
		for (String ignore : excludeUrlsList) {
			if (url.startsWith(ignore) || url.endsWith(ignore)) {
				return true;
			}
		}
		return false;
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		logger.info("登录开始，code为：" + code);
		String token = "";
		try {
			token = this.getToken();
		} catch (Exception e1) {
			logger.error("获取token失败:", e1);
			return;
		}
		logger.info("token为" + token);
		// 使用友空间免登接口获得用户信息
		String url = upesnBaseUrl + "/certified/userInfo/" + code
				+ "?access_token=" + token;
		try {
			String string = HttpClientUtils.doGet(url, String.class);
			logger.info("certified userInfo response：" + string);
			JSONObject json = (JSONObject) JSONObject.parse(string);
			json = json.getJSONObject("data");
			UserInfo userInfo = new UserInfo();
			userInfo.setMemberId(json.getString("member_id"));
			userInfo.setAvatar(json.getString("avatar"));
			userInfo.setName(json.getString("name"));
			userInfo.setEmail(json.getString("email"));
			userInfo.setDeptName(json.getString("dept_name"));
			ThreadUserInfo.setCurrentUserInfo(userInfo);
			this.saveUserInfo(userInfo);
			cache.setAndExpireInPipeline(CODE_PREFIX + code,
					JSONObject.toJSONString(userInfo), 24 * 60 * 60);
			Cookie usercode = new Cookie("code", code);
			usercode.setPath("/");
			usercode.setMaxAge(-1);
			String str = URLEncoder.encode(json.getString("name"), "UTF-8");
			Cookie name = new Cookie("name", str);
			name.setPath("/");
			name.setMaxAge(-1);
			Cookie pic = new Cookie("pic", json.getString("avatar"));
			pic.setPath("/");
			pic.setMaxAge(-1);
			Cookie userid = new Cookie("userid", json.getString("member_id"));
			userid.setPath("/");
			userid.setMaxAge(-1);

			response.addCookie(name);
			response.addCookie(pic);
			response.addCookie(userid);
			response.addCookie(usercode);

		} catch (Exception e) {
			logger.error("LoginFilter登录失败", e);
		}
	}

	private void saveUserInfo(UserInfo userInfo) {
		UserService userservice =(UserService) SpringInit.getApplicationContext().getBean("userService");
		
		userservice.save(new UserVO(userInfo));
	}

	/**
	 * @see Filter#init(FilterConfig) 初始化方法
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		// 处理不需要拦截的路径
		String excludeUrls = filterConfig.getInitParameter(EXCLUDEURLS);
		this.excludeUrlsList = Arrays.asList(excludeUrls.split(","));

		ServletContext sc = filterConfig.getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(sc);
		// 缓存
		if (cache == null) {
			cache = cxt.getBean(CacheManager.class);
		}

		if (authAppSuites == null) {
			authAppSuites = cxt.getBean(AuthAppSuitesImp.class);
		}
		
//		if (webTokenProcessor == null) {
//			SpringInit.setApplicationContext(cxt);
//			webTokenProcessor = cxt.getBean(DefaultTokenPorcessor.class);
//		}

		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("conf.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("LoginFilter加载conf.properties失败", e);
		}

		if (StringUtils.isBlank(upesnBaseUrl)) {
			upesnBaseUrl = properties.getProperty("isv_auth_base_path");
		}

		if (StringUtils.isBlank(suiteKey)) {
			suiteKey = properties.getProperty("isv_auth_suite_key");
		}

		if (StringUtils.isBlank(upesn_appid)) {
			upesn_appid = properties.getProperty("upesn.appid");
		}

		if (StringUtils.isBlank(upesn_secret)) {
			upesn_secret = properties.getProperty("upesn.secret");
		}

	}

	/**
	 * 获取ACCESS_TOKEN
	 * 
	 * @return
	 */
	private String getToken() {
		String access_token = cache.get(ACCESS_TOKEN);
		if (StringUtils.isBlank(access_token)) {
			String tokenUrl = upesnBaseUrl + "/token/?appid=" + upesn_appid
					+ "&secret=" + upesn_secret;
			logger.info("get tokenUrl is :" + tokenUrl);
			JSONObject rep = HttpClientUtils.doGet(tokenUrl, JSONObject.class);
			logger.info("获取token返回响应体为：" + rep);
			Token token = JSONObject.parseObject(rep.getString("data"),
					Token.class);
			access_token = token.getAccess_token();
			cache.setAndExpireInPipeline(ACCESS_TOKEN, token.getAccess_token(),
					token.getExpiresIn() / 2);
		}
		return access_token;
	}

}
