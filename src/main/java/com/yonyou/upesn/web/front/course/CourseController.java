package com.yonyou.upesn.web.front.course;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.appbase.web.BaseController;
import com.yonyou.upesn.entity.course.Course;
import com.yonyou.upesn.service.course.CourseService;

@Controller("FrontCourseController")
@RequestMapping(value = "/front/Course")
public class CourseController extends BaseController {
	@Autowired
	private CourseService courseService;
	
	
	
	@RequestMapping(value = "/SearchCourse", method = RequestMethod.GET)
	@ResponseBody
	public Object searchCourse(@RequestParam(value = "pageIndex", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize, HttpServletRequest request,
			HttpServletResponse response){ 	
		DateTimeUtil.getMonthStartDate();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, "COURSE_PUBLISH_TIME","DESC");
		//通过通过不同字段排序
		String field=request.getParameter("field");
		String sortOrder=request.getParameter("sortOrder");

		if(field!=null && !field.equals("")&&sortOrder!=null && !sortOrder.equals("")){
			pageRequest = buildPageRequest(pageNumber, pageSize, field,sortOrder);
		}
		Page<Course> page=courseService.searchCourse(pageRequest, request);
		
		return buildSuccess(page);
		
	}
	
	
	/**
	 * 查询课程详情
	 * 顺便记录课程行为
	 * 01:点击课程
	 */
	@RequestMapping(value = "/SearchCourseById", method = RequestMethod.GET)
	public @ResponseBody Object searchCourseById(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> resultreal = new HashMap<String, Object>();
		String courseId=request.getParameter("courseId");
		String useId=request.getParameter("useId");
//		courseId="123123123";
		useId="snowing";
		Boolean flag=courseService.courseBehavior("01",useId,courseId);
		List<Course> result=courseService.searchCourseById(courseId);
		Boolean Likeflag=courseService.searchCourseBehavior(useId,courseId);
		if(result.isEmpty()||flag==false){
			resultreal.put("flag", "false");
			return buildGlobalError(resultreal.toString());
		}
		resultreal.put("flag", "sussece");
		resultreal.put("result", result);
		resultreal.put("Likeflag", Likeflag);
		return  buildSuccess(resultreal);
	}
	/**
	 * 新增/修改一个课程
	 */
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object saveCourse(@RequestBody Course entity,HttpServletRequest request,
			HttpServletResponse response){
		String rootPath = request.getServletContext().getRealPath("/")+ File.separator + "doc";
		Boolean flag=courseService.saveCourse(rootPath,entity);
		if(flag){
			return buildSuccess("操作成功");
		}
		return buildGlobalError("操作失败");
	}
	/**
	 * 删除多个课程／一个课程
	 */	
	@RequestMapping(value = "/delCourse", method = RequestMethod.GET)
	public @ResponseBody Object delCourse(HttpServletRequest request,
			HttpServletResponse response){
		String courseIdList=request.getParameter("courseIdList");
		String rootPath = request.getServletContext().getRealPath("/")+ File.separator + "doc";
		Boolean flag=courseService.delCourse(rootPath,courseIdList);
		if(flag){
			return buildSuccess("操作成功");
		}
		return buildGlobalError("操作失败");
	}
	/**
	 * 启用多个课程／一个课程
	 */	
	@RequestMapping(value = "/UseCourse", method = RequestMethod.GET)
	public @ResponseBody Object isUseCourse(HttpServletRequest request,
			HttpServletResponse response){
		String courseIdList=request.getParameter("courseIdList");
		Boolean flag=courseService.isUseCourse(courseIdList,"1");
		if(flag){
			return buildSuccess("操作成功");
		}
		return buildGlobalError("操作失败");
	}
	/**
	 * 禁用多个课程／一个课程
	 */	
	@RequestMapping(value = "/stopUseCourse", method = RequestMethod.GET)
	public @ResponseBody Object stopUseCourse(HttpServletRequest request,
			HttpServletResponse response){
		String courseIdList=request.getParameter("courseIdList");
		Boolean flag=courseService.isUseCourse(courseIdList,"0");
		if(flag){
			return buildSuccess("操作成功");
		}
		return buildGlobalError("操作失败");
	}
	/**
	 * 记录课程行为
	 * 01:点击课程02:播放课程03:课程点赞
	 */	
	@RequestMapping(value = "/CourseBehavior", method = RequestMethod.GET)
	public @ResponseBody Object courseBehavior(HttpServletRequest request,
			HttpServletResponse response){
		String courseBehaviorType=request.getParameter("courseBehaviorType");
		String useId=request.getParameter("useId");
		String courseId=request.getParameter("courseId");
//		courseBehaviorType="01";
		useId="snowing";
//		courseId="123123123";
		if(StringUtils.isEmpty(courseBehaviorType)||StringUtils.isEmpty(courseId)||StringUtils.isEmpty(courseId)){
			return buildGlobalError("操作失败");
		}
		Boolean flag=courseService.courseBehavior(courseBehaviorType,useId,courseId);
		if(flag){
			return buildSuccess("操作成功");
		}
		return buildGlobalError("操作失败");
	}

}
