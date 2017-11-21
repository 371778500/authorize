package com.yonyou.exam.paper.repostitory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.yonyou.exam.paper.entity.KnowledgeTypeVo;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.FastBeanHelper;

@Repository
public class KnowledgeTypeVoDao {
	
	@Autowired
	private BaseDAO dao;
	
    public List<KnowledgeTypeVo> findAll() {
        return dao.queryAll(KnowledgeTypeVo.class);
    }

    public List<KnowledgeTypeVo> findByFid(String id) {
        String sql = "select * from exam_knowledge_type where pk_knowledge_type_parentid=?";  //parent_id为主表自关联字段
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(id);
        List<KnowledgeTypeVo> list = dao.queryByClause(KnowledgeTypeVo.class, sql, sqlparam);
        return list;
    }
    

    public Page<KnowledgeTypeVo> selectAllByPage(PageRequest pageRequest, Map<String, Object> searchParams) {
        StringBuilder sql = new StringBuilder("select * from exam_knowledge_type"); 
        SQLParameter sqlparam = new SQLParameter();
        if (null != searchParams && !searchParams.isEmpty()) {
            sql.append(" where ");
            for (Entry<String, Object> key : searchParams.entrySet()) {
                sql.append(FastBeanHelper.getColumn(KnowledgeTypeVo.class, key.getKey()));
                sql.append(" like ? AND ");
                sqlparam.addParam("%" + searchParams.get(key) + "%");
            }
            sql.delete(sql.length() - 4, sql.length());
        }
        return dao.queryPage(sql.toString(), sqlparam, pageRequest, KnowledgeTypeVo.class);
    }

    public void batchInsert(List<KnowledgeTypeVo> addList) throws DAOException {
        dao.insert(addList);
    }

    public void batchUpdate(List<KnowledgeTypeVo> updateList) {
        dao.update(updateList);
    }

    public void batchDelete(List<KnowledgeTypeVo> list) {
        dao.remove(list);
    }


}
