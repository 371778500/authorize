package com.yonyou.upesn.dao.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.upesn.entity.comment.CourseComment;

@Repository
public class CourseCommentDao {
	
	@Autowired
	private BaseDAO dao;
	
	/**
	 * 通过课程id获取该课程的评论列表
	 * @param courseId
	 * @return
	 */
	public List<CourseComment> searchCourseComment(String courseId) {
		StringBuffer sb=new StringBuffer("select  t.*  from course_comment t where course_id='");
		sb.append(courseId);
		sb.append("' order by t.create_time desc");
		return dao.queryByClause(CourseComment.class, sb.toString());
		
	}
	
	/**
	 * 保存评论
	 * @param entity
	 * @return
	 */
	public boolean saveComment(CourseComment entity){
		boolean flag=true;
		try{
			dao.insert(entity);
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	

}
