package com.yonyou.exam.userscore.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yonyou.appbase.user.service.UserService;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.appbase.web.BaseController;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.exam.paper.service.PaperMainVoService;
import com.yonyou.exam.userscore.entity.UserAnswerVO;
import com.yonyou.exam.userscore.entity.UserScoreVO;
import com.yonyou.exam.userscore.service.UserScoreService;
import com.yonyou.exam.userscore.vo.CommitTestParam;
import com.yonyou.exam.userscore.vo.ContentUIVO;
import com.yonyou.exam.userscore.vo.DisplayAnswerUIVO;
import com.yonyou.exam.userscore.vo.DisplayQAUIVO;
import com.yonyou.exam.userscore.vo.UserRankUIVO;
import com.yonyou.exam.userscore.vo.UserScoreUIVO;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;

@Controller
@RequestMapping(value = "/front/UserScore")
public class UserScoreController extends BaseController {

	@Autowired
	private UserService userserivce;

	@Autowired
	private UserScoreService scoreservice;

	@Autowired
	private PaperMainVoService paperservice;

	public static Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	@RequestMapping(value = "/saveUserScore", method = RequestMethod.POST)
	public @ResponseBody Object saveUserScore(@RequestBody String str) {

		CommitTestParam param = JSON.parseObject(str, CommitTestParam.class);

		String pk_exam_paper_main = param.getTest_id();
		String test_time = param.getTest_time();
		List<ContentUIVO> contentdatas = Arrays.asList(param.getContents());

		// 取得当前用户
		String email = userserivce.getUserinfo().getEmail();

		// 取得当前用户已经回答的试卷结果
		UserScoreVO answered = scoreservice.getUserScore(pk_exam_paper_main,
				email);
		// 取得该试卷
		PaperMainVo paper = paperservice.getPaperMainByPk(pk_exam_paper_main);
		if (answered != null) {
			// 试卷已经回答过

			// 根据试卷标准答案计算分数 ，如果分数大于用户已经考试过的记录，则更新考试结果

			UserScoreVO currentscore = answered.clone();

			this.calcUserScore(currentscore, paper, contentdatas);
			Integer answeredtesttime = answered.getTest_time();// 已经答卷消耗时间
			Integer currenttesttime = Integer.decode(test_time);// 当前消耗时间
			if (currentscore.getUser_score() > answered.getUser_score()) {
				// 得分高，更新次数和时间，以及成绩明细
				currentscore
						.setAnsweredcount(currentscore.getAnsweredcount() + 1);
				currentscore.setTest_time(currenttesttime);
				currentscore.calcUserScore();
				scoreservice.updateScoreWithAnswerlist(currentscore);
			} else if (currentscore.getUser_score() < answered.getUser_score()) {
				// 得分低，只更新次数
				answered.setAnsweredcount(answered.getAnsweredcount() + 1);
				scoreservice.updateScore(answered);
			} else {
				// 得分相等
				if (currenttesttime < answeredtesttime) {
					// 时间短，更新次数、时间和明细
					currentscore.setAnsweredcount(currentscore
							.getAnsweredcount() + 1);
					currentscore.setTest_time(currenttesttime);
					currentscore.calcUserScore();
					scoreservice.updateScoreWithAnswerlist(currentscore);
				} else {
					// 时间相等，更新次数
					answered.setAnsweredcount(answered.getAnsweredcount() + 1);
					scoreservice.updateScore(answered);
				}
			}

		} else {
			// 第一次回答试卷，插入考试结果
			UserScoreVO currentscore = this.getCurrentScore(paper,
					contentdatas, test_time, email);
			currentscore.calcUserScore();
			scoreservice.insertScore(currentscore);
		}

		return buildSuccess();
	}

	private UserScoreVO getCurrentScore(PaperMainVo paper,
			List<ContentUIVO> contentdatas, String test_time, String email) {
		// 取得答题MAP
		Map<String, String> contentmap = this.getContentMap(contentdatas);
		UserScoreVO result = new UserScoreVO(paper, Integer.decode(test_time),
				email, contentmap);

		return result;

	}

	private void calcUserScore(UserScoreVO currentscore, PaperMainVo paper,
			List<ContentUIVO> contentdatas) {
		if (null == currentscore.getId_useranswervo()) {

			logger.error("没有取得试卷考试成绩");
			return;
		}
		if (null == contentdatas) {
			logger.error("没有取得当前考试结果");
			return;
		}
		if (currentscore.getId_useranswervo().size() != contentdatas.size()) {
			logger.error("已经回答试卷的题目数量不等于当前试卷的题目数量");
			return;
		}

		// 取得答题MAP
		Map<String, String> contentmap = this.getContentMap(contentdatas);

		// 取得问题MAP
		Map<String, ExamQuestionVo> questionmap = this.getQuestionMap(paper);

		// 循环计算得分
		List<UserAnswerVO> answers = currentscore.getId_useranswervo();
		String key = null;
		String answercontent = null;
		ExamQuestionVo question = null;
		for (UserAnswerVO answer : answers) {
			key = answer.getPk_exam_exam_question();
			if (contentmap.containsKey(key)) {
				answercontent = contentmap.get(key);
			} else {
				answercontent = null;
			}

			if (questionmap.containsKey(key)) {
				question = questionmap.get(key);
			} else {
				question = null;
			}

			// 设置答案和计算得分
			answer.setAnswer(answercontent);
			answer.setScore(question.calcScore(answercontent));

		}

		currentscore.calcUserScore();

	}

	private Map<String, String> getContentMap(List<ContentUIVO> contentdatas) {
		Map<String, String> result = new HashMap<String, String>();
		String key = null;
		String value = null;
		for (ContentUIVO content : contentdatas) {
			key = content.getQuestionid();
			if (StrUtil.isEmpty(key)) {
				logger.error("遇到当前试卷中没有问题ID");
				continue;
			}
			value = content.getAnswer();
			if (StrUtil.isEmpty(value)) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	private Map<String, ExamQuestionVo> getQuestionMap(PaperMainVo paper) {
		Map<String, ExamQuestionVo> result = new HashMap<String, ExamQuestionVo>();
		List<ExamQuestionVo> questions = paper.getId_examquestionvo();
		for (ExamQuestionVo question : questions) {
			result.put(question.getPk_exam_exam_question(), question);
		}
		return result;
	}

	@RequestMapping(value = "/getScore", method = RequestMethod.GET)
	@ResponseBody
	public Object getScore(
			@RequestParam(value = "test_id") String pk_exam_paper_main,
			HttpServletRequest request, HttpServletResponse response) {

		if (StrUtil.isEmpty(pk_exam_paper_main)) {
			return buildError("", "参数为空", RequestStatusEnum.FAIL_GLOBAL);
		}
		// 取得当前用户email
		String email = userserivce.getUserinfo().getEmail();

		// 取得当前用户已经回答的试卷结果
		UserScoreVO answered = scoreservice.getUserScore(pk_exam_paper_main,
				email);

		if (null == answered) {
			return buildError("", "未取得用户考试信息", RequestStatusEnum.FAIL_GLOBAL);
		}
		// 取得该试卷
		PaperMainVo paper = paperservice.getPaperMainByPk(pk_exam_paper_main);
		if (null == paper) {
			return buildError("", "未取得试卷信息", RequestStatusEnum.FAIL_GLOBAL);
		}

		UserScoreUIVO result = new UserScoreUIVO(answered, paper);
		return buildSuccess(result);
	}

	@RequestMapping(value = "/getAnswerList", method = RequestMethod.GET)
	@ResponseBody
	public Object getAnswerList(
			@RequestParam(value = "test_id") String pk_exam_paper_main,
			HttpServletRequest request, HttpServletResponse response) {
		if (StrUtil.isEmpty(pk_exam_paper_main)) {
			return buildError("", "参数为空", RequestStatusEnum.FAIL_GLOBAL);
		}

		List<DisplayAnswerUIVO> result = new ArrayList<DisplayAnswerUIVO>();

		// 取得当前用户email
		String email = userserivce.getUserinfo().getEmail();
		// 取得当前用户已经回答的试卷结果
		UserScoreVO answeredscore = scoreservice.getUserScore(
				pk_exam_paper_main, email);
		if (null == answeredscore) {
			return buildError("", "未取得用户考试信息", RequestStatusEnum.FAIL_GLOBAL);
		}
		List<UserAnswerVO> answerlist = answeredscore.getId_useranswervo();
		for (UserAnswerVO answer : answerlist) {
			result.add(new DisplayAnswerUIVO(answer));
		}
		return buildSuccess(result);

	}

	@RequestMapping(value = "/getQuestionAndAnswer", method = RequestMethod.GET)
	@ResponseBody
	public Object getQuestionAndAnswer(
			@RequestParam(value = "pk_answer") String pk_exam_user_answer,
			HttpServletRequest request, HttpServletResponse response) {
		if (StrUtil.isEmpty(pk_exam_user_answer)) {
			return buildError("", "参数为空", RequestStatusEnum.FAIL_GLOBAL);
		}
		// 取得指定答案
		UserAnswerVO answer = scoreservice.getAnswerByPK(pk_exam_user_answer);
		if (null == answer) {
			return buildError("", "未取得指定答案", RequestStatusEnum.FAIL_GLOBAL);
		}

		// 根据问题PK取得问题
		ExamQuestionVo question = paperservice.getQuestionByPK(answer
				.getPk_exam_exam_question());
		if (null == question) {
			return buildError("", "未取得指定问题", RequestStatusEnum.FAIL_GLOBAL);
		}

		DisplayQAUIVO result = new DisplayQAUIVO(question, answer);
		return buildSuccess(result);
	}

	@RequestMapping(value = "/getUserRank", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserRank(
			@RequestParam(value = "test_id") String pk_exam_paper_main,
			HttpServletRequest request, HttpServletResponse response) {

		if (StrUtil.isEmpty(pk_exam_paper_main)) {
			return buildError("", "参数为空", RequestStatusEnum.FAIL_GLOBAL);
		}
		
		// 取得所有用户试卷考试结果
		List<UserScoreVO> userscores = scoreservice.getAllUserScoreByPaper(
				pk_exam_paper_main);
		if (null == userscores || userscores.size() == 0) {
			return buildError("", "未取得用户成绩信息", RequestStatusEnum.FAIL_GLOBAL);
		}

		UserRankUIVO result = new UserRankUIVO(userserivce.getUserinfo(),userscores);

		return buildSuccess(result);
	}

}
