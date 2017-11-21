package com.yonyou.exam.userscore.repostitory;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.exam.userscore.entity.UserAnswerVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

@Repository
public class UserAnswerDAO {
	@Autowired
	private BaseDAO dao;

	public UserAnswerVO findById(String id) throws DAOException {

		String sql = "select * from exam_user_answer  where dr='0' and pk_exam_user_answer=?";
		SQLParameter sqlparam = new SQLParameter();
		sqlparam.addParam(id);
		List<UserAnswerVO> list = dao.queryByClause(UserAnswerVO.class, sql,
				sqlparam);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	public void delete(UserAnswerVO entity) {

		if (null != entity) {
			dao.remove(entity);
		}
	}

	public Page<UserAnswerVO> selectAllByPage(PageRequest pageRequest,
			Map<String, Object> searchParams) throws DAOException {

		String sql = " select * from exam_user_answer";
		SQLParameter sqlparam = new SQLParameter();

		if (!searchParams.isEmpty()) {
			sql = sql + " where ";
			for (String key : searchParams.keySet()) {
				sql = sql + FastBeanHelper.getColumn(UserAnswerVO.class, key)
						+ " like ? AND ";
				sqlparam.addParam("%" + searchParams.get(key) + "%");
			}
			sql = sql.substring(0, sql.length() - 4);
		}
		return dao.queryPage(sql, sqlparam, pageRequest, UserAnswerVO.class);
	}

	public List<UserAnswerVO> getUserAnswersByScorePk(String pk_exam_user_score) {
		SQLParameter sqlparam = new SQLParameter();
		sqlparam.addParam(pk_exam_user_score);
		String sql = " SELECT A.pk_exam_user_score, A.pk_exam_user_answer,A.pk_exam_exam_question,A.answer,A.ts,A.dr,A.score "
				+ " FROM exam_user_answer A "
				+ " INNER JOIN exam_exam_question Q ON A.pk_exam_exam_question = Q.pk_exam_exam_question"
				+ " WHERE A.pk_exam_user_score = ? order by  Q.question_num ";
		return dao.queryByClause(UserAnswerVO.class, sql, sqlparam);
	}
	
	public void batchDelete(List<UserAnswerVO> list) throws DAOException {

		dao.remove(list);
	}

}
