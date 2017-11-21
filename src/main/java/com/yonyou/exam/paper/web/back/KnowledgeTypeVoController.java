package com.yonyou.exam.paper.web.back;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.appbase.web.BaseController;
import com.yonyou.exam.paper.entity.KnowledgeTypeVo;
import com.yonyou.exam.paper.service.KnowledgeTypeVoService;

@Controller
@RequestMapping(value = "/KnowledgeTypeVo")
public class KnowledgeTypeVoController extends BaseController{
	  public static Logger logger = LoggerFactory.getLogger(KnowledgeTypeVoController.class);
	  
	  @Autowired
	    private KnowledgeTypeVoService service;

	  
	  /**
	     * 获取左树列表
	     * 
	     * @return
	     */
	    @RequestMapping(value = "/list", method = RequestMethod.GET)
	    public @ResponseBody Object loadTree() {
	        List<KnowledgeTypeVo> data = service.selectAll();
	        return buildSuccess(data);
	    }

	    /**
	     * 根据父节点获取子节点
	     * 
	     * @param pid
	     * @return
	     */
	    @RequestMapping(value = "/findByFid", method = RequestMethod.GET)
	    public @ResponseBody Object findByFid(@RequestBody String pid) {
	        List<KnowledgeTypeVo> data = service.findByFid(pid);
	        return buildSuccess(data);
	    }

	    /**
	     * 保存左树
	     * 
	     * @param list
	     * @return
	     */
	    @RequestMapping(value = "/save", method = RequestMethod.POST)
	    public @ResponseBody Object save(@RequestBody List<KnowledgeTypeVo> list) {
	        service.save(list);
	        return buildSuccess();
	    }

	    /**
	     * 删除左树对象
	     * 
	     * @param list
	     * @return
	     */
	    @RequestMapping(value = "/del", method = RequestMethod.POST)
	    public @ResponseBody Object del(@RequestBody List<KnowledgeTypeVo> list) {
	    	service.batchDeleteByPrimaryKey(list);
	        return buildSuccess();
	    }
	    

}
