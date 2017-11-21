package com.yonyou.exam.paper.repostitory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.userscore.entity.UserAnswerVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;

@Repository
public class QuestionDAO {

	@Autowired
	private BaseDAO dao;
	
	public ExamQuestionVo findById(String id) throws DAOException {

		String sql = "select * from exam_exam_question  where dr='0' and pk_exam_exam_question=?";
		SQLParameter sqlparam = new SQLParameter();
		sqlparam.addParam(id);
		List<ExamQuestionVo> list = dao.queryByClause(ExamQuestionVo.class, sql,
				sqlparam);
		return list == null || list.isEmpty() ? null : list.get(0);
	}
	
	/**
	 * 指定试卷中的试题插入到对应数据库
	 * @param examquestionList
	 * @return
	 */
	public String[] insertList(List<ExamQuestionVo> examquestionList) {
		String[] str = dao.insert(examquestionList);
		return str;
	}
}
