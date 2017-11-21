package com.yonyou.exam.paper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.exam.paper.entity.KnowledgeTypeVo;
import com.yonyou.exam.paper.repostitory.KnowledgeTypeVoDao;
import com.yonyou.iuap.mvc.type.SearchParams;

@Service
public class KnowledgeTypeVoService {
	
    @Autowired
    private KnowledgeTypeVoDao dao;
    
    

    public List<KnowledgeTypeVo> findByFid(String pk) {
        return dao.findByFid(pk);
    }
    

    /**
     * 查询组织分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<KnowledgeTypeVo> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        Page<KnowledgeTypeVo> pageResult = dao.selectAllByPage(pageRequest, searchParams.getSearchMap()) ;
		return pageResult;
    }

    /**
     * 查询所有组织
     * 
     * @return
     */
    public List<KnowledgeTypeVo> selectAll() {
        return dao.findAll();
    }

    /**
     * 保存组织
     * 
     * @param recordList
     */
    public void save(List<KnowledgeTypeVo> recordList) {
        List<KnowledgeTypeVo> addList = new ArrayList<>(recordList.size());
        List<KnowledgeTypeVo> updateList = new ArrayList<>(recordList.size());
        for (KnowledgeTypeVo record : recordList) {
            if (record.getPk_exam_knowledge_type() == null) {
            	record.setPk_exam_knowledge_type(UUID.randomUUID().toString());
            	record.setDr(0);
                addList.add(record);
            } else {
                updateList.add(record);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	dao.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	dao.batchUpdate(updateList);
        }
    }

    /**
     * 删除组织
     * 
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<KnowledgeTypeVo> list) {
    	dao.batchDelete(list);
    }


}
