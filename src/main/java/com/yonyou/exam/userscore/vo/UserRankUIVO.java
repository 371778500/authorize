package com.yonyou.exam.userscore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.yonyou.appbase.user.entity.UserInfo;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.userscore.entity.UserScoreVO;

public class UserRankUIVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6418203479861395985L;

	private String user_name;
	private String img_url;
	private Integer duration = 0;
	private Integer score = 0;
	private Integer rank = 0;

	private List<UserRankUIVO> rank_list = new ArrayList<UserRankUIVO>();

	public UserRankUIVO() {
		super();
	}

	public UserRankUIVO(UserInfo userinfo, List<UserScoreVO> userscores) {
		super();
		if (null == userinfo) {
			return;
		}
		if (null == userscores || userscores.size() == 0) {
			return;
		}
		// 总体赋值排名
		int rank = 0;

		String currentemail = userinfo.getEmail();
		this.user_name = StrUtil.getNotNullValue(userinfo.getName());
		this.img_url = StrUtil.getNotNullValue(userinfo.getAvatar());
		this.rank_list = new ArrayList<UserRankUIVO>();
		for (UserScoreVO score : userscores) {
			rank++;
			this.rank_list.add(new UserRankUIVO(score, rank));
			if (currentemail.equals(score.getEmail())) {
				this.score = score.getUser_score();
				this.rank = rank;
				this.duration = score.getTest_time();
			}
		}

	}

	public UserRankUIVO( UserScoreVO score, int rank) {
		super();
		if (null == score) {
			return;
		}
		this.user_name = StrUtil.getNotNullValue(score.getName());
		this.img_url = StrUtil.getNotNullValue(score.getAvatar());

		this.duration = score.getTest_time();
		this.rank = rank;
		this.score = score.getUser_score();
		this.rank_list = new ArrayList<UserRankUIVO>();
	}

	/**
	 * @return user_name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * @param user_name
	 *            要设置的 user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * @return img_url
	 */
	public String getImg_url() {
		return img_url;
	}

	/**
	 * @param img_url
	 *            要设置的 img_url
	 */
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	/**
	 * @return duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            要设置的 duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            要设置的 score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * @return rank
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            要设置的 rank
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * @return rank_list
	 */
	public List<UserRankUIVO> getRank_list() {
		return rank_list;
	}

	/**
	 * @param rank_list
	 *            要设置的 rank_list
	 */
	public void setRank_list(List<UserRankUIVO> rank_list) {
		this.rank_list = rank_list;
	}

}
