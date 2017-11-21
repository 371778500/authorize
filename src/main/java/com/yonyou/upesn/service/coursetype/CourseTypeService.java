package com.yonyou.upesn.service.coursetype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.appbase.util.RandomGUID;
import com.yonyou.upesn.dao.coursetype.CourseTypeDao;
import com.yonyou.upesn.entity.coursetype.CourseType;
import com.yonyou.upesn.entity.coursetype.CoursetypeVO;

import javassist.expr.NewArray;

@Service
public class CourseTypeService {

	@Autowired
	private CourseTypeDao dao;
	
	
	/**
	 * 新增/修改一个课程分类
	 */
	public boolean saveCourseType(CourseType entity){
		boolean flag=true;
		try{
			
			if(entity.getPkCourseTypeParentid()==null || entity.getPkCourseTypeParentid().equals("")){
				entity.setPkCourseTypeParentid("root");//设置父类id为默认的root
			}
			if(entity.getPkCourseType()==null || entity.getPkCourseType().equals("")){
				entity.setPkCourseType(RandomGUID.NewGUID());
				entity.setDr("0");
				dao.save(entity);
				return flag;
			}
			dao.updete(entity);
		}catch(Exception e){
			e.printStackTrace();
			flag=false;
		}
		
		return flag;
	}
	
	/**
	 * 删除课程分类
	 */
	public boolean deleteCourseType(String id){
		List<CourseType> list=dao.queryCourseTypeByParentid(id);
		if(list.size()!=0){
			return false;
		}
		dao.delete(id);
		return true;
	}
	
	/**
	 * 查询所有课程分类
	 */
	public List<CourseType> QueryAllCourseType(){	
		return dao.QueryAllCourseType();
	}
//	/**
//	 * 查询所有课程分类(前台调用)
//	 * 支持三级分类
//	 */
//	public Map<String, Object> QueryAllCourseTypeToC(){
//		List<CoursetypeVO> coursetypelistTwo=new ArrayList<CoursetypeVO>();
//		List<CourseType> CourseTypeListTwo=dao.QueryAllCourseTypeTwo();
//		List<CourseType> CourseTypeListThree=dao.QueryAllCourseTypeThree();
//		for(CourseType ctl:CourseTypeListTwo){
//		
//			if(ctl.getPkCourseTypeParentid().equals("root")){
//				resultList.put("coursetypelistone", ctl);
//			}
//			else{
//				Map<String, Object> temp = new HashMap<>();
//				temp.put("coursetypelistTwo", ctl);
//				for(CourseType ctlThree:CourseTypeListThree){
//					i++;
//					if(ctl.getPkCourseTypeParentid().equals(ctl.getPkCourseType())){
//						temp.put("coursetypelistThree"+i, ctlThree);
//					}
//				}
//				coursetypelistTwo.add(temp);
//			}
//		}
//		resultList.put("coursetypelistTwo", coursetypelistTwo);
//		
//		return resultList;
//	}

	/**
	 * 查询所有课程分类
	 */
	public List<CoursetypeVO> QueryAllCourseTypeToC(){
		
		List<CourseType> list=dao.QueryAllCourseType();
		CoursetypeVO vo=new CoursetypeVO();
		CoursetypeVO coursetypevo=getCoursetypeVO(vo,"root",list);
	
		return 	vo.getSublist();
		
	}
	public CoursetypeVO getCoursetypeVO(CoursetypeVO vo,String id,List<CourseType> list){
		int i;
		for(i=0;i<list.size();i++){
			if(id.equals(list.get(i).getPkCourseTypeParentid())){
				CoursetypeVO vo2=new CoursetypeVO();
				vo2.setId(list.get(i).getPkCourseType());
				vo2.setName(list.get(i).getCourseTypeName());
				vo2.putSubList(getCoursetypeVO(vo2,vo2.getId(),list));
				vo.putSubList(vo2);
			}
		}
		return new CoursetypeVO();		
	}

}
