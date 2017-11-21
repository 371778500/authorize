package com.yonyou.exam.userscore.repostitory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.userscore.entity.UserAnswerVO;
import com.yonyou.exam.userscore.entity.UserScoreVO;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.bs.dao.MetadataDAO;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Repository
public class UserScoreDAO {
	@Autowired
	private BaseDAO dao;

	@Transactional
	public UserScoreVO save(UserScoreVO entity) {
		if (entity.getPk_exam_user_score() == null) {
			entity.setStatus(VOStatus.NEW);
			entity.setPk_exam_user_score(UUID.randomUUID().toString());
			entity.setDr("0");// 未删除标识
		} else {
			entity.setStatus(VOStatus.UPDATED);
		}

		if (entity.getId_useranswervo() != null
				&& entity.getId_useranswervo().size() > 0) {
			for (UserAnswerVO child : entity.getId_useranswervo()) {
				if (child.getPk_exam_user_answer() == null) {
					child.setPk_exam_user_score(entity.getPk_exam_user_score());
					child.setStatus(VOStatus.NEW);
					child.setDr(entity.getDr());
				} else {
					child.setStatus(VOStatus.UPDATED);
				}
			}
			dao.save(
					entity,
					entity.getId_useranswervo()
							.toArray(
									new UserAnswerVO[entity
											.getId_useranswervo().size()]));

		} else {
			dao.save(entity);
		}
		return entity;
	}

	public int delete(UserScoreVO entity) throws Exception {

		if (null == entity) {
			return 0;
		}
		dao.remove(entity);
		return 1;
	}

	public Page<UserScoreVO> selectAllByPage(PageRequest pageRequest,
			Map<String, Object> searchParams) throws DAOException {

		String sql = " select * from exam_user_score"; // user_name = ?
		SQLParameter sqlparam = new SQLParameter();

		if (null != searchParams && !searchParams.isEmpty()) {
			sql = sql + " where ";
			for (String key : searchParams.keySet()) {
				sql = sql + FastBeanHelper.getColumn(UserScoreVO.class, key)
						+ " like ? AND ";
				sqlparam.addParam("%" + searchParams.get(key) + "%");
			}
			sql = sql.substring(0, sql.length() - 4);
		}
		return dao.queryPage(sql, sqlparam, pageRequest, UserScoreVO.class);
	}

	public void batchDelete(List<UserScoreVO> list) throws DAOException {

		dao.remove(list);
	}

	public void batchDelChild(List<UserScoreVO> list) throws DAOException {
		SQLParameter sqlparam = new SQLParameter();
		String deleteSQL = SQLHelper.createDeleteIn("exam_user_answer",
				"fk_id_useranswervo", list.size());
		for (UserScoreVO item : list) {
			sqlparam.addParam(item.getPk_exam_user_score());
		}
		dao.update(deleteSQL, sqlparam);
	}

	public UserScoreVO getUserScore(String pk_exam_paper_main, String email) {

		SQLParameter sqlparam = new SQLParameter();
		sqlparam.addParam(pk_exam_paper_main);
		sqlparam.addParam(email);

		String sql = " SELECT * FROM exam_user_score "
				+ " WHERE pk_exam_paper_main = ? AND email = ? AND dr = '0' ";
		List<UserScoreVO> result = dao.queryByClause(UserScoreVO.class, sql,
				sqlparam);
		if (null == result || result.size() != 1) {
			return null;
		}

		return result.get(0);
	}

	public List<UserScoreVO> getAllUserScoreByPaper(String pk_exam_paper_main) {
		List<UserScoreVO> result = new ArrayList<UserScoreVO>();
		if (StrUtil.isEmpty(pk_exam_paper_main)) {
			return result;
		}
		SQLParameter sqlparam = new SQLParameter();
		sqlparam.addParam(pk_exam_paper_main);

		String sql = " SELECT u.name AS name,u.avatar AS avatar, "
				+ " score.pk_exam_user_score , score.pk_exam_paper_main, "
				+ " score.pk_space , score.email, "
				+ " score.test_time , score.answeredcount, "
				+ " score.ts , score.dr, "
				+ " score.user_score "
				+ "	FROM exam_user_score score "
				+ " INNER JOIN sm_user u ON score.email = u.email "
				+ "	WHERE score.pk_exam_paper_main = ? "
				+ " AND score.dr = '0'	ORDER BY score.user_score DESC,score.test_time ASC ";
		result = dao.queryByClause(UserScoreVO.class, sql, sqlparam);
		return result;
	}

}
