package com.yonyou.exam.paper.repostitory;

import com.yonyou.appbase.util.DateTimeUtil;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.paper.entity.KnowledgeTypeVo;
import com.yonyou.exam.paper.entity.PaperMainVo;
import com.yonyou.exam.paper.vo.MainPageParam;
import com.yonyou.iuap.persistence.bs.dao.BaseDAO;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.jdbc.framework.SQLParameter;
import com.yonyou.iuap.persistence.jdbc.framework.util.SQLHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public class PaperMainVoDao {

    @Autowired
    private BaseDAO dao;

    /**
     * 根据某一非主键字段查询实体
     */
    public List<PaperMainVo> findByPaper_name(String paper_name) {

        String sql = "select * from exam_paper_main where paper_name=?";
        SQLParameter sqlparam = new SQLParameter();
        sqlparam.addParam(paper_name);
        List<PaperMainVo> list = dao.queryByClause(PaperMainVo.class, sql,
                sqlparam);
        return list;
    }

    /**
     * 分页相关
     */
    /*
	 * public Page<PaperMainVo> selectAllByPage( PageRequest pageRequest ,
	 * Map<String, Object> searchParams ) throws DAOException {
	 * 
	 * String sql = " select * from exam_paper_main "; // user_name = ?
	 * SQLParameter sqlparam = new SQLParameter();
	 * 
	 * if (null != searchParams && !searchParams.isEmpty()) { sql = sql +
	 * " where "; for (String key : searchParams.keySet()) { if
	 * (key.equalsIgnoreCase("searchParam")) { sql = sql +
	 * "(paper_name like ? ) AND "; for (int i = 0; i < 4; i++) {
	 * sqlparam.addParam("%" + searchParams.get(key) + "%"); } } else if
	 * (key.equalsIgnoreCase("fk_id_papermainvo")) { sql = sql +
	 * FastBeanHelper.getColumn(PaperMainVo.class, key) + " in ("; String value
	 * = (String) searchParams.get(key); String[] ids = value.split(","); for
	 * (String s : ids) { sql = sql + "? ,"; sqlparam.addParam(s); } sql =
	 * sql.substring(0, sql.length() - 1); sql = sql + ") AND"; } } sql =
	 * sql.substring(0, sql.length() - 4); } return dao.queryPage(sql, sqlparam,
	 * pageRequest, PaperMainVo.class); }
	 */

    /**
     * 批量操作
     */
    public void batchInsert(List<PaperMainVo> addList) throws DAOException {

        dao.insert(addList);
    }

    public void batchUpdate(List<PaperMainVo> updateList) throws DAOException {

        dao.update(updateList);
    }

    public void batchDelete(List<PaperMainVo> list) throws DAOException {

        dao.remove(list);
    }

    /**
     * 根据 参照主键id 查询所属机构
     */
    public List<KnowledgeTypeVo> selectKnowledgeTypeVoByIds(Set<String> ids) {

        String sql = SQLHelper.createSQLIn("exam_knowledge_type",
                "pk_exam_knowledge_type", ids.size());
        SQLParameter sqlparam = new SQLParameter();
        for (String id : ids) {
            sqlparam.addParam(id);
        }
        return dao.queryByClause(KnowledgeTypeVo.class, sql, sqlparam);
    }

    // start =============
    public Page<PaperMainVo> searchPaperMain(PageRequest pageRequest,
                                             HttpServletRequest request) {
        SQLParameter sqlparam = new SQLParameter();
        StringBuffer sb = new StringBuffer(
                "select  t.*  from exam_paper_main t where t.dr='0'");
        sb.append(setParam(request));
        return dao.queryPage(sb.toString(), sqlparam, pageRequest,
                PaperMainVo.class);
    }

    /**
     * 设置查询中的条件
     *
     * @param request
     * @return
     */
    public String setParam(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();

        // 获取分类search_fk_id_papermainvo
        String search_fk_id_papermainvo = request
                .getParameter("search_fk_id_papermainvo");
        if (search_fk_id_papermainvo != null
                && !search_fk_id_papermainvo.equals("")) {
            sb.append(" AND t.pk_exam_knowledge_type IN (");
            String[] ids = search_fk_id_papermainvo.split(",");
            for (int i = 0; i < ids.length; i++) {
                sb.append("'");
                sb.append(ids[i]);
                sb.append("'");
                if (i != ids.length - 1) {
                    sb.append(",");
                }
            }
            sb.append(") ");
        }

        // 通过课程name查询
        String papername = request.getParameter("search_searchParam");
        if (papername != null && !papername.equals("")) {
            sb.append(" AND t.paper_name LIKE '%");
            sb.append(papername);
            sb.append("%' ");

        }

        // 通过可见性查询

        // 查询时间段
        String month = request.getParameter("month");
        if (month != null && !month.equals("")) {
            int monthInt = Integer.parseInt(month);
            String startData = "2000-01-01";
            String endData = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int year = calendar.get(Calendar.YEAR);
            if (monthInt == 0) {// 获取今年之前的数据
                endData = year + "-01-01";
            }
            if (monthInt != 0 && monthInt < 10) {
                startData = year + "-0" + month + "-01";
                endData = year + "-0" + month + "-32";
            }
            if (monthInt >= 10) {
                startData = year + "-" + month + "-01";
                endData = year + "-" + month + "-32";
            }

            sb.append(" AND t.upload_date>'");
            sb.append(startData);
            sb.append("' AND t.upload_date<'");
            sb.append(endData);
            sb.append("'");
        }
        // 按照月或者周搜索
        String sortWeekorMoth = request.getParameter("selecteTimeId");
        if (sortWeekorMoth != null && !sortWeekorMoth.equals("")) {
            String startData = "2000-01-01";
            String endData = DateTimeUtil.getNowDate();
            if (sortWeekorMoth.equals("week")) {
                startData = DateTimeUtil.getWeekStartDate();
            }
            if (sortWeekorMoth.equals("month")) {
                startData = DateTimeUtil.getMonthStartDate();
            }
            if (sortWeekorMoth.equals("before")) {
                endData = DateTimeUtil.getMonthStartDate();
            }
            sb.append(" AND t.upload_date>'");
            sb.append(startData);
            sb.append("' AND t.upload_date<'");
            sb.append(endData);
            sb.append("'");
        }

        // 是否后台来的，启用的筛选
        String backstage = request.getParameter("Backstage");
        if (backstage == null || ("").equals(backstage)) {
            sb.append(" and t.using_status='1' ");
        }

        return sb.toString();
    }

    // end =============

    // ======================== 移动端后台 ====================================

    /**
     * 设置查询中的条件
     *
     * @param request
     * @param search_text
     * @param start_time
     * @param end_time
     * @return
     */
    public String setParamMobile(HttpServletRequest request,
                                 String search_text, String start_time, String end_time) {
        StringBuffer sb = new StringBuffer();

        // 通过试卷name查询
        if (search_text != null && !search_text.equals("")) {
            sb.append(" AND p.paper_name LIKE '%");
            sb.append(search_text);
            sb.append("%' ");

        }

        return sb.toString();
    }

    public Page<PaperMainVo> searchAnsweredPaper(PageRequest pageRequest,
                                                 MainPageParam param, HttpServletRequest request) {
        SQLParameter sqlparam = new SQLParameter();
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT p.pk_exam_paper_main,p.total_score,"
                + " p.pass_score,p.exam_time,p.begin_time,p.end_time,p.paper_name,p.examnum,s.answeredcount,p.upload_date,p.checkanswer_status,p.checkscore_status "
                + " FROM exam_paper_main p "
                + " INNER JOIN exam_user_score s ON p.pk_exam_paper_main = s.pk_exam_paper_main "
                + " WHERE p.dr='0'  AND p.using_status = '1' ");

        sb.append(" AND  s.email = ? ");
        sqlparam.addParam(param.getEmail());

        if (!StrUtil.isEmpty(param.getSearch_text())) {
            sb.append(" AND p.paper_name LIKE ? ");
            sqlparam.addParam("%" + param.getSearch_text() + "%");
        }
        if (param.getStart_time() != null) {
            sb.append(" AND p.begin_time >= ? ");
            sqlparam.addParam(DateTimeUtil.parse(param.getStart_time(),
                    "yyyy-MM-dd HH:mm:ss"));
        }
        if (param.getEnd_time() != null) {
            sb.append(" AND p.begin_time <= ? ");
            sqlparam.addParam(DateTimeUtil.parse(param.getEnd_time(),
                    "yyyy-MM-dd HH:mm:ss"));
        }

        return dao.queryPage(sb.toString(), sqlparam, pageRequest,
                PaperMainVo.class);
    }

    public Page<PaperMainVo> searchUnAnsweredPaper(PageRequest pageRequest,
                                                   MainPageParam param, HttpServletRequest request) {
        SQLParameter sqlparam = new SQLParameter();
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT p.pk_exam_paper_main,p.total_score,p.pass_score,p.exam_time,p.begin_time,p.end_time,p.paper_name,p.examnum,p.upload_date,p.checkanswer_status,p.checkscore_status "
                + " FROM exam_paper_main p WHERE p.dr='0'  AND p.using_status = '1' ");
        if (!StrUtil.isEmpty(param.getSearch_text())) {
            sb.append(" AND p.paper_name LIKE ? ");
            sqlparam.addParam("%" + param.getSearch_text() + "%");
        }
        if (param.getStart_time() != null) {
            sb.append(" AND p.begin_time >= ? ");
            sqlparam.addParam(DateTimeUtil.parse(param.getStart_time(),
                    "yyyy-MM-dd HH:mm:ss"));
        }
        if (param.getEnd_time() != null) {
            sb.append(" AND p.begin_time <= ? ");
            sqlparam.addParam(DateTimeUtil.parse(param.getEnd_time(),
                    "yyyy-MM-dd HH:mm:ss"));
        }

        sb.append(" AND NOT EXISTS ( " + " SELECT s.pk_exam_paper_main "
                + " FROM exam_user_score s " + " WHERE s.email = ? "
                + " AND s.pk_exam_paper_main = p.pk_exam_paper_main)");
        sqlparam.addParam(param.getEmail());

        return dao.queryPage(sb.toString(), sqlparam, pageRequest,
                PaperMainVo.class);
    }

    /**
     * 移动后端：是否开始答题
     *
     * @param test_id
     * @return
     */
    public PaperMainVo getPaperMainByPk(String test_id) {
        if (StrUtil.isEmpty(test_id)) {
            return null;
        }
        PaperMainVo result = dao.queryByPK(PaperMainVo.class, test_id);
        if (null == result) {
            return result;
        }
        //查询试题
        List<ExamQuestionVo> questions = dao.queryByClause(ExamQuestionVo.class, " SELECT * FROM exam_exam_question WHERE pk_exam_paper_main = '" + test_id + "'  ORDER BY question_num ASC ");
        result.setId_examquestionvo(questions);
        return result;

    }
}
