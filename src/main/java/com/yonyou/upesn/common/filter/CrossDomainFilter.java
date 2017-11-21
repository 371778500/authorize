package com.yonyou.upesn.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * 解决跨域问题
 * @author luochp3
 *
 */

public class CrossDomainFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
//
//	@Override
//	public void destroy() {
//		// TODO 自动生成的方法存根
//		
//	}

//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res,
//			FilterChain chain) throws IOException, ServletException {
//		
//		HttpResponse response=(HttpResponse)res;
//		response.setHeader("Access-Control-Allow-Origin", "*");  
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
//        response.setHeader("Access-Control-Max-Age", "3600");  
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");  
//        chain.doFilter(req, res);  
//		
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		// TODO 自动生成的方法存根
//		
//	}

}
