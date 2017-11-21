package com.yonyou.upesn.service.comment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.appbase.user.entity.ThreadUserInfo;
import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.appbase.util.RandomGUID;
import com.yonyou.upesn.dao.comment.CourseCommentDao;
import com.yonyou.upesn.entity.comment.CourseComment;

@Service
public class CourseCommentService {

	@Autowired
	private CourseCommentDao dao;
	
	/**
	 * 通过课程id获取该课程的评论列表
	 * @param courseId
	 * @return
	 */
	public List<CourseComment> searchCourseComment(String courseId) {
		return dao.searchCourseComment(courseId);
		
	}
	
	/**
	 * 保存评论
	 * @param entity
	 * @return
	 */
	public Map<String,Object> saveComment(String courseId,String content){
		Map<String,Object> map=new HashMap<String,Object>();
		CourseComment courseComment=new CourseComment();
		courseComment.setId(RandomGUID.NewGUID());
		courseComment.setContent(content);
		courseComment.setCourseId(courseId);
		UserInfo user=ThreadUserInfo.getCurrentUserInfo();
		if(user!=null){
			courseComment.setUserId(user.getMemberId());
			courseComment.setUserName(user.getName());
			courseComment.setUserPic(user.getAvatar());
		}
		courseComment.setDr("0");
		courseComment.setCreateTime(new Date());
		courseComment.setTs(new Date());
		boolean flag=dao.saveComment(courseComment);
		map.put("flag", flag);
		map.put("date", courseComment);
		return map;
	}
}

