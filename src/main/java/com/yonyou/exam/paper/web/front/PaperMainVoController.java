package com.yonyou.exam.paper.web.front;

import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.appbase.user.service.UserService;
import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.appbase.util.Utf8Utils;
import com.yonyou.appbase.util.enumutil.EnumUtils;
import com.yonyou.appbase.util.enumutil.EnumVo;
import com.yonyou.appbase.web.BaseController;
import com.yonyou.exam.paper.entity.Checkanswer_status;
import com.yonyou.exam.paper.entity.Checkscore_status;
import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.exam.paper.entity.Using_status;
import com.yonyou.exam.paper.service.PaperMainVoService;
import com.yonyou.exam.paper.vo.MainPageParam;
import com.yonyou.exam.paper.vo.PaperMainUIVo;
import com.yonyou.exam.paper.vo.PaperUIVO;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller("FrontPaperMainVoController")
@RequestMapping(value = "/front/PaperMainVo")
public class PaperMainVoController extends BaseController {

	public static Logger logger = LoggerFactory
			.getLogger(PaperMainVoController.class);

	@Autowired
	private PaperMainVoService papermainservice;

	@Autowired
	private UserService userserivce;

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public @ResponseBody Object del(@RequestBody List<PaperMainVo> list) {
		papermainservice.batchDeleteByPrimaryKey(list);
		return buildSuccess();
	}

	private Map<String, Integer> getUserTestedCount(List<String> exampaperidlist) {
		Map<String, Integer> result = new Hashtable<String, Integer>();
		UserInfo userinfo = userserivce.getUserinfo();
		// TODO:根据人员和试卷ID列表查询人员已经考试次数
		for (String exampaperid : exampaperidlist) {
			result.put(exampaperid, 5);
		}
		return result;
	}

	/** 查询枚举值 */
	@RequestMapping(value = "/loadEnum", method = RequestMethod.GET)
	@ResponseBody
	public Object loadEnum() throws Exception {
		Map<String, List<EnumVo>> map = EnumUtils.loadEnum(Using_status.class);
		return super.buildMapSuccess(map);
	}

	/** 查询枚举值 */
	@RequestMapping(value = "/loadEnum1", method = RequestMethod.GET)
	@ResponseBody
	public Object loadEnum1() throws Exception {
		Map<String, List<EnumVo>> map = EnumUtils
				.loadEnum(Checkanswer_status.class);
		return super.buildMapSuccess(map);
	}

	/** 查询枚举值 */
	@RequestMapping(value = "/loadEnum2", method = RequestMethod.GET)
	@ResponseBody
	public Object loadEnum2() throws Exception {
		Map<String, List<EnumVo>> map = EnumUtils
				.loadEnum(Checkscore_status.class);
		return super.buildMapSuccess(map);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody List<PaperMainVo> list) {
		papermainservice.save(list);
		return buildSuccess();
	}

	/**
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	// @RequestMapping(value = "/list", method = RequestMethod.GET)
	// public @ResponseBody Object page(PageRequest pageRequest, SearchParams
	// searchParams) {
	// Page<PaperMainVo> tmpdata = childService.selectAllByPage(pageRequest,
	// searchParams);
	// Page<PaperMainVo> data= childService.selectKnowledgeTypeVo(tmpdata) ;
	// return buildSuccess(data);
	// }
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object searchPaperMain(
			@RequestParam(value = "pageIndex", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		DateTimeUtil.getMonthStartDate();
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				"upload_date", "DESC");
		// 通过通过不同字段排序
		String field = request.getParameter("field");
		String sortOrder = request.getParameter("sortOrder");
		if (field != null && !field.equals("") && sortOrder != null
				&& !sortOrder.equals("")) {
			pageRequest = buildPageRequest(pageNumber, pageSize, field,
					sortOrder);
		}
		Page<PaperMainVo> page = papermainservice.searchPaperMain(pageRequest,
				request);

		return buildSuccess(page);

	}

	// ================== 移动端后台 ========================
	/**
	 * 移动端后台
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/searchPaperMainMobile")
	@ResponseBody
	public Object searchPaperMainMobile(
			@RequestParam(value = "page_index", defaultValue = "1") String pageNumber,
			@RequestParam(value = "range_num", defaultValue = "20") String pageSize,
			@RequestParam(value = "complete") String complete,
			@RequestParam(value = "search_text") String search_text,
			@RequestParam(value = "start_time") String start_time,
			@RequestParam(value = "end_time") String end_time,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		PageRequest pageRequest = buildPageRequest(
				Integer.parseInt(pageNumber), Integer.parseInt(pageSize),
				"upload_date", "DESC");
		// 通过不同字段排序
		String field = request.getParameter("field");
		String sortOrder = request.getParameter("sortOrder");

		if (field != null && !field.equals("") && sortOrder != null
				&& !sortOrder.equals("")) {
			pageRequest = buildPageRequest(Integer.parseInt(pageNumber),
					Integer.parseInt(pageSize), field, sortOrder);
		}

		UserInfo userinfo = userserivce.getUserinfo();// 取得用户信息

		MainPageParam param = new MainPageParam();

		if (!StrUtil.isEmpty(search_text)) {
			
			//测试环境使用
			//logger.debug("before----"+search_text);
			//param.setSearch_text(Utf8Utils.detectUtf8(search_text));
			//logger.debug("after----"+param.getSearch_text());
			
			//正式环境使用
			param.setSearch_text(search_text);
			
		}

		if (!StrUtil.isEmpty(start_time)) {
			param.setStart_time(DateTimeUtil.StrToDateTime(start_time
					+ " 00:00:00"));
		}
		if (!StrUtil.isEmpty(end_time)) {
			param.setEnd_time(DateTimeUtil
					.StrToDateTime(end_time + " 23:59:59"));
		}

		param.setEmail(userinfo.getEmail());
		param.setAnswered(complete.equals("0") ? false : true);

		Page<PaperMainVo> page = papermainservice.searchPaper4Mobile(
				pageRequest, param, request);

		List<PaperMainUIVo> uiList = new ArrayList<PaperMainUIVo>();// 试卷管理移动端过滤
		List<PaperMainVo> content = page.getContent();// 获取从数据库返回的所有数据
		List<String> exampaperidlist = new ArrayList<String>();
		for (PaperMainVo data : content) {
			exampaperidlist.add(data.getPk_exam_paper_main());
			uiList.add(new PaperMainUIVo(data));
		}

		return buildSuccess(uiList);

	}

	/**
	 * 移动端后台：是否开始答题
	 * 
	 * @param test_id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPaper")
	@ResponseBody
	public Object getPaper(@RequestParam String test_id,
			HttpServletRequest request, HttpServletResponse response) {

		PaperMainVo paper = papermainservice.getPaperMainByPk(test_id);

		if (null == paper) {
			return buildError("", "未取得试卷", RequestStatusEnum.FAIL_GLOBAL);
		}
		Date currentdate = DateTimeUtil.getCurrentDate();
		if (currentdate.before(paper.getBegin_time())) {
			return buildError("", "考试未开始", RequestStatusEnum.FAIL_GLOBAL);
		}
		if (currentdate.after(paper.getEnd_time())) {
			return buildError("", "考试已结束", RequestStatusEnum.FAIL_GLOBAL);
		}

		PaperUIVO result = new PaperUIVO(paper);
		return buildSuccess(result);

	}

}
