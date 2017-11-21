package com.yonyou.appbase.web;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.yonyou.appbase.user.entity.ThreadUserInfo;
import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonErrorResponse;
import com.yonyou.iuap.mvc.type.JsonResponse;

/**
 * controller父类 用于处理公用逻辑，例如错误处理等信息
 *
 * Created by zengxs on 2016/11/14.
 */
public class BaseController {

	/**
	 * 包装错误信息
	 * 
	 * @param field
	 * @param msg
	 * @param status
	 * @return
	 */
	public JsonResponse buildError(String field, String msg, RequestStatusEnum status) {
		JsonErrorResponse errorResponse = new JsonErrorResponse();
		if (RequestStatusEnum.SUCCESS.equals(status)) {
			throw new IllegalArgumentException("状态码设置错误!");
		}
		errorResponse.setSuccess(status.getStatus());
		if (RequestStatusEnum.FAIL_GLOBAL.equals(status)) {
			errorResponse.setMessage(StringEscapeUtils.unescapeHtml(msg));
		} else {
			errorResponse.getDetailMsg().put(StringEscapeUtils.unescapeHtml(field), StringEscapeUtils.unescapeHtml(msg));
		}
		return errorResponse;
	}

	/**
	 * 包装错误信息
	 *
	 * @param msg
	 * @return
	 */
	public JsonResponse buildGlobalError(String msg) {
		JsonErrorResponse errorResponse = new JsonErrorResponse();
		errorResponse.setMessage(StringEscapeUtils.escapeHtml(msg));
		return errorResponse;
	}

	/**
	 * 包装错误信息
	 *
	 * @param msgMap
	 * @param status
	 * @return
	 */
	public JsonResponse buildError(Map<String, String> msgMap, RequestStatusEnum status) {
		JsonErrorResponse errorResponse = new JsonErrorResponse();
		if (RequestStatusEnum.SUCCESS.equals(status)) {
			throw new IllegalArgumentException("状态码设置错误!");
		}
		errorResponse.setSuccess(status.getStatus());
		for (Map.Entry<String, String> entry : msgMap.entrySet()) {
			errorResponse.getDetailMsg().put(StringEscapeUtils.escapeHtml(entry.getKey()),
					StringEscapeUtils.escapeHtml(entry.getValue()));
		}
		return errorResponse;
	}

	/**
	 * 包装成功返回信息
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JsonResponse buildSuccess(String key, Object value) {
		JsonResponse response = new JsonResponse();
		response.getDetailMsg().put(key, value);
		return response;
	}

	/**
	 * 包装分页成功返回信息
	 *
	 * @param value
	 * @return
	 */
	public <T> JsonResponse buildSuccess(Object value) {
		JsonResponse response = new JsonResponse();
		response.getDetailMsg().put("data", value);
		UserInfo userInfo = ThreadUserInfo.getCurrentUserInfo();
		if(userInfo!=null&&!userInfo.equals("")){
			String avatar = userInfo.getAvatar();
			response.getDetailMsg().put("avatar", avatar);
		}
		return response;
	}

	public <T> JsonResponse buildSuccess() {
		JsonResponse response = new JsonResponse();
		return response;
	}

	/**
	 * 包装成功返回信息
	 * 
	 * @param msgMap
	 * @return
	 */
	public JsonResponse buildMapSuccess(Map msgMap) {
		JsonResponse response = new JsonResponse();
		response.setDetailMsg(msgMap);
		return response;
	}

	/**
	 * 创建分页请求.
	 * 
	 * @param sortOrder
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, String field, String sortOrder) {
		Sort sort = null;
		if (sortOrder.equals("ASC")) {
			sort = new Sort(Direction.ASC, field);
		} else if (sortOrder.equals("DESC")) {
			sort = new Sort(Direction.DESC, field);
		} else {
			sort = new Sort(Direction.ASC, field);
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
}
