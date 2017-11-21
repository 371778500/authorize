package com.yonyou.upesn.dao.coursetype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.upesn.entity.course.Course;
import com.yonyou.upesn.entity.coursetype.CourseType;

@Repository
public class CourseTypeDao {

	@Autowired
	private BaseDAO dao;
	
	/**
	 * 查询所有课程分类
	 */
	public List<CourseType> QueryAllCourseType(){
		StringBuffer sb=new StringBuffer("select * from course_type where dr='0' order by COURSE_TYPE_CODE asc ");
		return dao.queryByClause(CourseType.class,sb.toString());
	}
	/**
	 * 查询所有课程二级分类
	 */
	public List<CourseType> QueryAllCourseTypeTwo(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.* FROM course_type b where ");
		sb.append("b.PK_COURSE_TYPE_PARENTID=(SELECT ");
		sb.append("a.PK_COURSE_TYPE FROM course_type a where ");
		sb.append("a.PK_COURSE_TYPE_PARENTID='root' ");
		sb.append("and a.dr='0')and b.DR='0' or b.PK_COURSE_TYPE_PARENTID='root'");
		return dao.queryByClause(CourseType.class,sb.toString());	
	}
	/**
	 * 查询所有课程三级分类
	 */
	public List<CourseType> QueryAllCourseTypeThree(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.* FROM course_type c where c.PK_COURSE_TYPE_PARENTID in ");
		sb.append("(SELECT b.PK_COURSE_TYPE FROM course_type b where ");
		sb.append("b.PK_COURSE_TYPE_PARENTID=(SELECT ");
		sb.append("a.PK_COURSE_TYPE FROM course_type a where ");
		sb.append("a.PK_COURSE_TYPE_PARENTID='root' ");
		sb.append("and a.dr='0')and b.DR='0')and c.DR='0'");
		return dao.queryByClause(CourseType.class,sb.toString());
	}
	
	/**
	 * 保存课程分类
	 */
	@Transactional
	public void save(CourseType entity){
		dao.insert(entity);
	}
	/**
	 * 修改课程分类
	 */
	@Transactional
	public void updete(CourseType entity){
		dao.update(entity);
	}
	
	/**
	 * 删除课程分类
	 */
	@Transactional
	public void delete(String id){
		CourseType entity=dao.queryByPK(CourseType.class, id);
		if(entity!=null && entity.getPkCourseType()!=null){
			dao.remove(entity);
		}
	}
	
	/**
	 * 根据父节点获取子节点
	 */
	public List<CourseType> queryCourseTypeByParentid(String id){
		StringBuffer sql=new StringBuffer("select * from course_type where PK_COURSE_TYPE_PARENTID= '");
		sql.append(id);
		sql.append("'");
		return dao.queryByClause(CourseType.class, sql.toString());
	}

	
}
