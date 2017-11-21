package com.yonyou.exam.userscore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.exam.userscore.entity.UserAnswerVO;
import com.yonyou.exam.userscore.entity.UserScoreVO;
import com.yonyou.exam.userscore.repostitory.UserAnswerDAO;
import com.yonyou.exam.userscore.repostitory.UserScoreDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;

@Service
public class UserScoreService {

	@Autowired
	private UserScoreDAO userscoredao;

	@Autowired
	private UserAnswerDAO useranswerdao;

	public UserScoreVO getUserScore(String pk_exam_paper_main, String email) {
		UserScoreVO result = userscoredao.getUserScore(pk_exam_paper_main,
				email);
		if (null == result) {
			return null;
		}
		// 取得子表
		List<UserAnswerVO> answers = useranswerdao
				.getUserAnswersByScorePk(result.getPk_exam_user_score());
		result.setId_useranswervo(answers);
		return result;

	}

	public void updateScore(UserScoreVO currentscore) {

		userscoredao.save(currentscore);
	}

	public void insertScore(UserScoreVO currentscore) {
		currentscore.setPk_exam_user_score(null);

		List<UserAnswerVO> answerlist = currentscore.getId_useranswervo();
		for (UserAnswerVO answer : answerlist) {
			answer.setPk_exam_user_answer(null);

		}

		userscoredao.save(currentscore);
	}

	public void updateScoreWithAnswerlist(UserScoreVO currentscore) {
		if (null == currentscore) {
			return;
		}
		List<UserAnswerVO> answerlist = currentscore.getId_useranswervo();
		if (answerlist != null) {
			// 删除原来的用户答案
			useranswerdao.batchDelete(currentscore.getId_useranswervo());
		}
		// 调整用户答案列表为新插入数据
		for (UserAnswerVO answer : answerlist) {
			answer.setPk_exam_user_answer(null);
		}
		userscoredao.save(currentscore);

	}

	public UserAnswerVO getAnswerByPK(String pk_exam_user_answer) {
		return useranswerdao.findById(pk_exam_user_answer);
	}

	public List<UserScoreVO> getAllUserScoreByPaper(String pk_exam_paper_main) {
		return userscoredao.getAllUserScoreByPaper(pk_exam_paper_main);
	}

}
