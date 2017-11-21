package com.yonyou.exam.paper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.paper.entity.KnowledgeTypeVo;
import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.exam.paper.repostitory.PaperMainVoDao;
import com.yonyou.exam.paper.repostitory.QuestionDAO;
import com.yonyou.exam.paper.vo.MainPageParam;

@Service
public class PaperMainVoService {

	@Autowired
	private PaperMainVoDao childDao;

	@Autowired
	private QuestionDAO questionDAO;

	/**
	 * 根据某一非主键字段查询实体
	 */
	public List<PaperMainVo> findByPaper_name(String paper_name) {
		return childDao.findByPaper_name(paper_name);
	}

	/**
	 * 分页相关
	 */
	/*
	 * public Page<PaperMainVo> selectAllByPage(PageRequest pageRequest,
	 * SearchParams searchParams) { Page<PaperMainVo> pageResult =
	 * childDao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
	 * return pageResult; }
	 */

	/**
	 * 保存（插入和更新）
	 */
	public void save(List<PaperMainVo> recordList) {
		List<PaperMainVo> addList = new ArrayList<>(recordList.size());
		List<PaperMainVo> updateList = new ArrayList<>(recordList.size());
		for (PaperMainVo record : recordList) {
			if (record.getPk_exam_paper_main() == null) {
				record.setPk_exam_paper_main(UUID.randomUUID().toString());
				record.setDr("0");
				record.setUpload_date(DateTimeUtil.getCurrentDateTime());
				record.setPk_exam_knowledge_type(record.getFk_id_papermainvo());
				addList.add(record);
			} else {
				updateList.add(record);
			}
		}
		if (CollectionUtils.isNotEmpty(addList)) {
			childDao.batchInsert(addList);
		}
		if (CollectionUtils.isNotEmpty(updateList)) {
			childDao.batchUpdate(updateList);
		}
	}

	/**
	 * 批量删除
	 */
	public void batchDeleteByPrimaryKey(List<PaperMainVo> list) {
		childDao.batchDelete(list);
	}

	/**
	 * 根据联系人信息查询所属机构
	 * 
	 * @param userJobPage
	 * @return
	 */
	/*
	 * public Page<PaperMainVo> selectKnowledgeTypeVo(Page<PaperMainVo> data) {
	 * List<PaperMainVo> list = data.getContent(); Set<String> ids = new
	 * HashSet<String>(); for (PaperMainVo child : list) { if
	 * (!StringUtil.isEmpty(child.getFk_id_papermainvo())) {
	 * ids.add(child.getFk_id_papermainvo()); } } if (!ids.isEmpty()) {
	 * List<KnowledgeTypeVo> pList = this.selectKnowledgeTypeVoByIds(ids);
	 * for(PaperMainVo child : list){ for(KnowledgeTypeVo parent:pList){
	 * if(child
	 * .getFk_id_papermainvo()!=null&&child.getFk_id_papermainvo().equalsIgnoreCase
	 * (parent.getPk_exam_knowledge_type())){ //
	 * child.setInstitName(parent.getInstitName()); //子表的对应主表字段塞入主表的显示名称 } } }
	 * return data; } return null; }
	 */
	public List<KnowledgeTypeVo> selectKnowledgeTypeVoByIds(Set<String> ids) {
		return childDao.selectKnowledgeTypeVoByIds(ids);
	}

	public Page<PaperMainVo> searchPaperMain(PageRequest pageRequest,
			HttpServletRequest request) {
		Page<PaperMainVo> page = childDao.searchPaperMain(pageRequest, request);
		return page;
	}

	// =================== 移动端后台 =====================

	public Page<PaperMainVo> searchPaper4Mobile(PageRequest pageRequest,
			MainPageParam param, HttpServletRequest request) {
		Page<PaperMainVo> result = null;
		if (null == param) {
			return result;
		}

		if (param.isAnswered()) {
			// 已经答卷
			result = childDao.searchAnsweredPaper(pageRequest, param, request);
		} else {
			result = childDao
					.searchUnAnsweredPaper(pageRequest, param, request);
		}

		return result;
	}

	/**
	 * 移动后端：是否开始答题
	 * 
	 * @param test_id
	 * @param string
	 * @return
	 */
	public PaperMainVo getPaperMainByPk(String test_id) {
		return childDao.getPaperMainByPk(test_id);
	}

	public ExamQuestionVo getQuestionByPK(String pk_exam_exam_question) {
		return questionDAO.findById(pk_exam_exam_question);
	}

}
