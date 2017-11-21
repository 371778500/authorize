package com.yonyou.upesn.web.front.coursetype;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.appbase.web.BaseController;
import com.yonyou.upesn.entity.coursetype.CourseType;
import com.yonyou.upesn.entity.coursetype.CoursetypeVO;
import com.yonyou.upesn.service.coursetype.CourseTypeService;

@Controller("FrontCourseTypeController")
@RequestMapping(value="/front/coursetype")
public class CourseTypeController extends BaseController{
	
	@Autowired
	private CourseTypeService service;
	
	
	/**
	 * 查询所有课程分类
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Object QueryAllCourseType(){
		List<CourseType> list=service.QueryAllCourseType();
		return buildSuccess(list);
	}
	/**
	 * 查询所有课程分类
	 */
	@RequestMapping(value = "/listToC", method = RequestMethod.GET)
	public @ResponseBody Object QueryAllCourseTypetoC(){
		List<CoursetypeVO> list=service.QueryAllCourseTypeToC();
		return   buildSuccess(list.get(0));
	}
	
	/**
	 * 新增/修改一个课程分类
	 */
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object saveCourseType(@RequestBody CourseType entity){
		Boolean flag=service.saveCourseType(entity);
		if(flag){
			return buildSuccess("操作成功");
		}
		return buildGlobalError("操作失败");
	}
	
	
	/**
	 * 删除课程分类
	 */
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public @ResponseBody Object deleteCourseType(ServletRequest request){
		String id=request.getParameter("id");
		Boolean flag=service.deleteCourseType(id);
		request.getParameterMap();
		if(flag){
			return buildSuccess("删除成功");
		}
		return buildGlobalError("该节点下有子节点，不可删除");
	}

}
