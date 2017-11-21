package com.yonyou.exam.paper.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.paper.entity.PaperMainVo;

public class PaperMainUIVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String paper_name;// 试卷名称
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date start_time;// 试卷有效开始时间
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date end_time;// 试卷有效结束时间
	private Integer limit_duration;// 考试时间
	private Integer total_points;// 总成绩
	private Integer pass_points;// 通过分数
	private Integer residue_times;// 可考次数
	private String test_id;// 试卷ID
	private Integer checkscore_status;// 是否可查看分数 1：可以查看分数 0：不可查看分数

	public PaperMainUIVo() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public PaperMainUIVo(PaperMainVo data) {
		this.paper_name = StrUtil.getNotNullValue(data.getPaper_name());
		this.total_points = data.getTotal_score();
		this.pass_points = data.getPass_score();
		this.end_time = data.getEnd_time();
		this.residue_times = data.getExamnum()
				- (null == data.getAnsweredcount() ? 0 : data
						.getAnsweredcount());// 初始化为可考次数
		this.start_time = data.getBegin_time();
		this.limit_duration = data.getExam_time();
		this.test_id = StrUtil.getNotNullValue(data.getPk_exam_paper_main());
		this.checkscore_status = Integer.parseInt(data.getCheckscore_status());
	}

	public void calcuteResidueTimes(Integer testedcount) {
		this.residue_times -= testedcount;
	}

	/**
	 * @return paper_name
	 */
	public String getPaper_name() {
		return paper_name;
	}

	/**
	 * @param paper_name
	 *            要设置的 paper_name
	 */
	public void setPaper_name(String paper_name) {
		this.paper_name = paper_name;
	}

	/**
	 * @return start_time
	 */
	public Date getStart_time() {
		return start_time;
	}

	/**
	 * @param start_time
	 *            要设置的 start_time
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	/**
	 * @return end_time
	 */
	public Date getEnd_time() {
		return end_time;
	}

	/**
	 * @param end_time
	 *            要设置的 end_time
	 */
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	/**
	 * @return limit_duration
	 */
	public Integer getLimit_duration() {
		return limit_duration;
	}

	/**
	 * @param limit_duration
	 *            要设置的 limit_duration
	 */
	public void setLimit_duration(Integer limit_duration) {
		this.limit_duration = limit_duration;
	}

	/**
	 * @return total_points
	 */
	public Integer getTotal_points() {
		return total_points;
	}

	/**
	 * @param total_points
	 *            要设置的 total_points
	 */
	public void setTotal_points(Integer total_points) {
		this.total_points = total_points;
	}

	/**
	 * @return pass_points
	 */
	public Integer getPass_points() {
		return pass_points;
	}

	/**
	 * @param pass_points
	 *            要设置的 pass_points
	 */
	public void setPass_points(Integer pass_points) {
		this.pass_points = pass_points;
	}

	/**
	 * @return residue_times
	 */
	public Integer getResidue_times() {
		return residue_times;
	}

	/**
	 * @param residue_times
	 *            要设置的 residue_times
	 */
	public void setResidue_times(Integer residue_times) {
		this.residue_times = residue_times;
	}

	/**
	 * @return test_id
	 */
	public String getTest_id() {
		return test_id;
	}

	/**
	 * @param test_id
	 *            要设置的 test_id
	 */
	public void setTest_id(String test_id) {
		this.test_id = test_id;
	}

	public Integer getCheckscore_status() {
		return checkscore_status;
	}

	public void setCheckscore_status(Integer checkscore_status) {
		this.checkscore_status = checkscore_status;
	}

}