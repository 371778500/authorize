package com.yonyou.upesn.web.front.comment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.appbase.web.BaseController;
import com.yonyou.upesn.entity.comment.CourseComment;
import com.yonyou.upesn.service.comment.CourseCommentService;

@Controller("FrontCourseCommentController")
@RequestMapping(value = "/front/comment")
public class CourseCommentController extends BaseController {
	
	@Autowired
	private CourseCommentService service;
	
	/**
	 * 通过课程id获取该课程的评论列表
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Object searchCourseComment(HttpServletRequest request,
			HttpServletResponse respons) {
		String courseId=request.getParameter("courseId");
		List<CourseComment> list=service.searchCourseComment(courseId);
		return buildSuccess(list);
		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public @ResponseBody Object saveComment(HttpServletRequest request,
			HttpServletResponse respons) {
		String content=request.getParameter("content");
		String courseid=request.getParameter("courseId");
		Map<String,Object> map=service.saveComment(courseid,content);
		
		return buildMapSuccess(map);
		
	}

}

