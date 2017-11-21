package com.yonyou.exam.paper.web.back;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.appbase.user.service.UserService;
import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.appbase.util.enumutil.EnumUtils;
import com.yonyou.appbase.util.enumutil.EnumVo;
import com.yonyou.appbase.web.BaseController;
import com.yonyou.exam.paper.entity.Checkanswer_status;
import com.yonyou.exam.paper.entity.Checkscore_status;
import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.exam.paper.entity.Using_status;
import com.yonyou.exam.paper.service.PaperMainVoService;

@Controller
@RequestMapping(value = "/PaperMainVo")
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
	 * @param pageRequest
	 * @param searchParams
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

}
