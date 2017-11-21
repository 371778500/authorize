package com.yonyou.upesn.dao.course;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.upesn.entity.course.Course;
import com.yonyou.upesn.entity.course.CourseBehavior;
import com.yonyou.upesn.entity.coursetype.CourseType;

@Repository
public class CourseDao {

	@Autowired
	private BaseDAO dao;
	
	/**
	 * 通过各种参数查询对应课程并分页
	 * 
	 * @param request
	 * @param pageRequest 
	 * @time 2017.7.25
	 */
	public Page<Course> searchCourse(PageRequest pageRequest,HttpServletRequest request) {
		SQLParameter sqlparam = new SQLParameter();
		StringBuffer sb=new StringBuffer("select  t.*  from course t where t.DR='0'");
		sb.append(setParam(request));
		return dao.queryPage(sb.toString(), sqlparam, pageRequest, Course.class);
		
	}
	
	/**
	 * 查询下来课程id对应的课程
	 * 
	 * @param request
	 * @param pageRequest 
	 * @time 2017.7.25
	 */
	public List<Course> searchCourse(String courseListnew) {
		StringBuffer sb=new StringBuffer("select  t.*  from course t where t.PK_COURSE in (");
		sb.append(courseListnew);
		sb.append(")");
		return dao.queryByClause(Course.class, sb.toString());
		
	}
	
	/**
	 * 设置查询中的条件
	 * @param request
	 * @return
	 */
	public String setParam(HttpServletRequest request){
		StringBuffer sb=new StringBuffer();
		
		//获取分类courseTypeIds
		String courseTypeIds=request.getParameter("courseTypeIds");
		if(courseTypeIds!=null && !courseTypeIds.equals("")){
			sb.append(" AND t.PK_COURSE_TYPE IN (");
			String[] ids=courseTypeIds.split(",");
			for(int i=0;i<ids.length;i++){
				sb.append("'");
				sb.append(ids[i]);
				sb.append("'");
				if(i!=ids.length-1){
					sb.append(",");
				}
			}
			sb.append(") ");
		}
		
		
		//通过课程name查询
		String coursename=request.getParameter("coursename");
		if(coursename!=null && !coursename.equals("")){
			sb.append(" AND t.COURSE_NAME LIKE '%");
			sb.append(coursename);
			sb.append("%' ");
			
		}
		
	
		//通过可见性查询
	
		//查询时间段
		String month=request.getParameter("month");
		if(month!=null && !month.equals("")){
			int monthInt=Integer.parseInt(month);
			String startData="2000-01-01";
			String endData=null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int year=calendar.get(Calendar.YEAR);
			if(monthInt==0){//获取今年之前的数据
				endData=year+"-01-01";
			}
			if(monthInt!=0 && monthInt<10){
				startData=year+"-0"+month+"-01";
				endData=year+"-0"+month+"-32";
			}
			if(monthInt>=10){
				startData=year+"-"+month+"-01";
				endData=year+"-"+month+"-32";
			}
			
			sb.append(" AND t.COURSE_PUBLISH_TIME>'");
			sb.append(startData);
			sb.append("' AND t.COURSE_PUBLISH_TIME<'");
			sb.append(endData);
			sb.append("'");
		}
		//按照月或者周搜索
		String sortWeekorMoth=request.getParameter("selecteTimeId");
		if(sortWeekorMoth!=null && !sortWeekorMoth.equals("")){
			String startData="2000-01-01";
			String endData=DateTimeUtil.getNowDate();
			if(sortWeekorMoth.equals("week")){
				startData=DateTimeUtil.getWeekStartDate();
			}
			if(sortWeekorMoth.equals("month"))
			{
				startData=DateTimeUtil.getMonthStartDate();
			}
			if(sortWeekorMoth.equals("before"))
			{
				endData=DateTimeUtil.getMonthStartDate();
			}
			sb.append(" AND t.COURSE_PUBLISH_TIME>'");
			sb.append(startData);
			sb.append("' AND t.COURSE_PUBLISH_TIME<'");
			sb.append(endData);
			sb.append("'");
			}
		
		//是否后台来的，启用的筛选
		String backstage=request.getParameter("Backstage");
		if(backstage==null||("").equals(backstage)){
			sb.append(" and t.is_use='1' ");
		}

		return sb.toString();
		
	}
	
	

	/**
	 * 通过课程名称模糊查找
	 * 
	 * @param name
	 * @return
	 * @author snowing
	 * @param pageRequest 
	 * @param pageRequest 
	 * @time 2017.7.25
	 */
	public Page<Course> searchCoursebyName(String name, PageRequest pageRequest) {
		Page<Course> result = null;
		StringBuffer sb = new StringBuffer();
		SQLParameter sqlparam = new SQLParameter();
		sb.append("select  t.*  from course t where DR='0' ");
		sb.append(" and  t.is_use='1' and t.COURSE_NAME LIKE'%");
		sb.append(name);
		sb.append("%' order by course_code asc");
		result =dao.queryPage(sb.toString(), sqlparam, pageRequest, Course.class);
		return result;
	}

	/**
	 * 通过课程类型模糊查找
	 * 
	 * @param courseTypeId
	 * @return
	 * @author snowing
	 * @param pageRequest
	 * @time 2017.7.25
	 */
	public Page<Course> searchCoursebyTypeId(StringBuffer courseTypeIdListnew, PageRequest pageRequest) {
		Page<Course> result = null;
		StringBuffer sb = new StringBuffer();
		SQLParameter sqlparam = new SQLParameter();
		sb.append("SELECT a.* FROM course a where a.PK_COURSE_TYPE IN (");
		sb.append(courseTypeIdListnew);
		sb.append(") and a.DR='0'  and  a.is_use='1'");
		result = dao.queryPage(sb.toString(), sqlparam, pageRequest, Course.class);
		return result;
	}
	/**
	 * 初始化全部课程
	 */
	public Page<Course> initAllCourse(PageRequest pageRequest) {
		Page<Course> result = null;
		StringBuffer sb = new StringBuffer();
		SQLParameter sqlparam = new SQLParameter();
		sb.append("select  t.*  from course t where DR='0'  and  t.is_use='1'");
		sb.append("order by course_code asc");
		result = dao.queryPage(sb.toString(), sqlparam, pageRequest, Course.class);
		return result;
	}
	/**
	 * 按照id查找课程
	 */
	public List<Course> searchCourseById(String courseId) {
		List<Course> result = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select  t.*  from course t where DR='0' ");
		sb.append("and t.PK_COURSE='");
		sb.append(courseId);
		sb.append("'  and  t.is_use='1'");
		result = dao.queryByClause(Course.class, sb.toString());
		return result;
	}
	/**
	 * 保存课程
	 */
	public void save(Course entity) {
		dao.insert(entity);	
	}
	/**
	 * 更新课程信息
	 */
	public void update(Course entity) {
		dao.update(entity);	
		
	}
	/**
	 * 删除多个课程／一个课程
	 */	
	public void delCourse(String rootpath,String courseListnew) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from course where PK_COURSE in (");
		sb.append(courseListnew);
		sb.append(")");
		dao.update(sb.toString());
		
	}
	/**
	 * 启用多个课程／一个课程
	 * @param isuse 
	 */	
	public void isUseCourse(String courseListnew, String isuse) {

		StringBuffer sb = new StringBuffer();
		sb.append("update course t set t.is_use='");
		sb.append(isuse);
		sb.append("' where t.PK_COURSE in (");
		sb.append(courseListnew);
		sb.append(")");
		dao.update(sb.toString());
		
	}
	/**
	 * 新增课程行为
	 */
	public void saveCourseBehavior(CourseBehavior entity) {
		dao.insert(entity);		
	}

	public void updateCourseClick(String courseId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update course t set t.COURSE_CLICK_NUMBER=t.COURSE_CLICK_NUMBER+1");
		sb.append(" where t.PK_COURSE='");
		sb.append(courseId);
		sb.append("'");
		dao.update(sb.toString());		
	}

	public void updateCourseLike(String courseId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update course t set t.COURSE_LIKE_NUMBER=t.COURSE_LIKE_NUMBER+1");
		sb.append(" where t.PK_COURSE='");
		sb.append(courseId);
		sb.append("'");
		dao.update(sb.toString());		
	}

	public void updateCoursePlay(String courseId) {
		StringBuffer sb = new StringBuffer();
		sb.append("update course t set t.COURSE_BROADCAST_NUMBER=t.COURSE_BROADCAST_NUMBER+1");
		sb.append(" where t.PK_COURSE='");
		sb.append(courseId);
		sb.append("'");
		dao.update(sb.toString());	
	}

	/**
	 * 是否点赞过
	 */
	public Boolean searchCourseBehavior(String useId, String courseId) {
		List<CourseBehavior> result = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM course_behavior t WHERE t.PK_COURSE='");
		sb.append(courseId);
		sb.append("' AND t.PK_USER='");
		sb.append(useId);
		sb.append("' AND t.COURSE_BEHAVIOR_TYPE='03' AND DR='0'");
		result = dao.queryByClause(CourseBehavior.class, sb.toString());
		if(result.isEmpty()){
			return false;
		}
		return true;
	}
}
