package com.yonyou.upesn.service.course;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.appbase.util.DeleteDirectory;
import com.yonyou.appbase.util.RandomGUID;
import com.yonyou.upesn.common.enums.TrueOrFalseEnum;
import com.yonyou.upesn.dao.course.CourseDao;
import com.yonyou.upesn.entity.course.Course;
import com.yonyou.upesn.entity.course.CourseBehavior;
import com.yonyou.upesn.entity.course.CourseBehaviorType;
import com.yonyou.upesn.entity.coursetype.CourseType;
import com.yonyou.upesn.service.file.NasService;

@Service
public class CourseService {

	@Autowired
	private CourseDao courseDao;
	@Autowired
	private NasService fileservice;

	@Transactional(readOnly = false)
	public Page<Course> searchCourse(PageRequest pageRequest, HttpServletRequest request) {
		Page<Course> page = courseDao.searchCourse(pageRequest, request);
		List<Course> list = page.getContent();
		for (Course c : list) {
			c.setIsuse(TrueOrFalseEnum.getTrueOrFalse(c.getIsuse()).getName());
		}
		return page;

	}

	@Transactional(readOnly = false)
	public List<Course> searchCourseById(String courseId) {
		List<Course> result = new ArrayList<Course>();
		if (StringUtils.isEmpty(courseId)) {
			return result;
		}
		result = courseDao.searchCourseById(courseId);
		return result;
	}

	/**
	 * 新增/修改一个课程
	 */
	@Transactional
	public Boolean saveCourse(String rootpath,Course entity) {
		boolean flag = true;
		try {
			if (entity.getPkCourse() == null || entity.getPkCourse().equals("")) {
				entity.setPkCourse(RandomGUID.NewGUID());// 设置主键
				entity.setCourseClickNumber(0);// 设置课程点击数
				entity.setCourseBroadcastNumber(0);// 设置课程播放数
				entity.setCourseLikeNumber(0);// 点赞数
				// entity.setCoursePublisher("用友大学"); //课程上传人，暂时设置为默认，后期更改
				entity.setIsuse("1");// 默认设置为启用
				entity.setDr("0");
				entity.setCoursePublishTime(new Date());// 设置课程上传时间
				entity.setTs(new Date());// 设置时间搓
				courseDao.save(entity);
			} else {
				if (entity.getIsuse().equals("是")) {
					entity.setIsuse("1");
				}
				if (entity.getIsuse().equals("否")) {
					entity.setIsuse("0");
				}

				List<Course> list = courseDao.searchCourseById(entity.getPkCourse());

				// 判断课程图片及视频是否被修改,如果被修改则删除之前的文件
				if (!list.isEmpty()) {
					if (!entity.getCourseIconUrl().equals(list.get(0).getCourseIconUrl())) {
						deleteFile(rootpath,list.get(0).getCourseIconUrl());
					}
					if (!entity.getCourseUrl().equals(list.get(0).getCourseUrl())) {
						entity.setCoursePublishTime(new Date());// 设置课程上传时间
						courseDao.update(entity);
						deleteFile(rootpath,list.get(0).getCourseUrl());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	/**
	 * 删除某个文件夹下的文件及该目录
	 * 
	 * @param url
	 */
	private void deleteFile(String rootpath,String url) {
		int last = url.lastIndexOf("\\");
		int first = url.lastIndexOf("doc") + 3;
		url = rootpath + url.substring(first, last);
		DeleteDirectory.deleteDir(new File(url)); // 删除文件夹及文件夹下的文件
	}

	/**
	 * 删除多个课程／一个课程
	 */
	public Boolean delCourse(String rootpath,String courseIdList) {
		boolean flag = true;
		try {
			List<Course> list = courseDao.searchCourse(getCourseIds(courseIdList));
			courseDao.delCourse(rootpath,getCourseIds(courseIdList));
			// 可以删除课程对应的上传文件
			for (Course c : list) {
				deleteFile(rootpath,c.getCourseIconUrl());
				deleteFile(rootpath,c.getCourseUrl());
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	/**
	 * 启用多个课程／一个课程
	 * 
	 * @param isuse
	 */
	@Transactional
	public Boolean isUseCourse(String courseIdList, String isuse) {
		boolean flag = true;
		try {
			courseDao.isUseCourse(getCourseIds(courseIdList), isuse);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	/**
	 * 通过前台参数解析获取课程ids并拼接
	 * 
	 * @return
	 */
	public String getCourseIds(String courseIdList) {
		String[] sourceStrArray = courseIdList.split(",");
		StringBuffer courseListnew = new StringBuffer();
		for (int i = 0; i < sourceStrArray.length; i++) {
			courseListnew.append("'");
			courseListnew.append(sourceStrArray[i]);
			courseListnew.append("'");
			if (i != sourceStrArray.length - 1) {
				courseListnew.append(",");
			}
		}
		return courseListnew.toString();
	}

	public Boolean courseBehavior(String courseBehaviorType, String useId, String courseId) {
		if (courseBehaviorType.equals(CourseBehaviorType.CLICK) || courseBehaviorType.equals(CourseBehaviorType.LIKE)
				|| courseBehaviorType.equals(CourseBehaviorType.PLAY)) {
			return false;
		}
		switch (courseBehaviorType) {
		case "01":
			courseDao.updateCourseClick(courseId);
			break;
		case "02":
			courseDao.updateCoursePlay(courseId);
			break;
		case "03":
			courseDao.updateCourseLike(courseId);
			break;

		}
		CourseBehavior entity = new CourseBehavior();
		entity.setPkCourseBehavior(RandomGUID.NewGUID());
		entity.setCourseBehaviorType(courseBehaviorType);
		entity.setPkUser(useId);
		entity.setPkCourse(courseId);
		entity.setCourseBehaviorTime(new Date());
		entity.setDR("0");
		courseDao.saveCourseBehavior(entity);
		return true;
	}

	/**
	 * 是否点赞过
	 */
	public Boolean searchCourseBehavior(String useId, String courseId) {
		Boolean Behaviorflag = courseDao.searchCourseBehavior(useId, courseId);
		return Behaviorflag;
	}

}
