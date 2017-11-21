package com.yonyou.upesn.entity.course;

import com.yonyou.appbase.util.StrUtil;

public class CourseBehaviorType {
	private static final String COURSE_CLICK= "01";
	private static final String COURSE_PLAY= "02";
	private static final String CODE_LIKE= "03";
	
	public static final CourseBehaviorType CLICK= new CourseBehaviorType(COURSE_CLICK, "点击课程");
	public static final CourseBehaviorType PLAY= new CourseBehaviorType(COURSE_PLAY, "播放客车");
	public static final CourseBehaviorType LIKE = new CourseBehaviorType(CODE_LIKE, "课程点赞");
	
	private String code;
	
	private String name;
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	private CourseBehaviorType(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	public boolean equals (CourseBehaviorType keyType) {
		if (null == keyType) {
			return false;
		}
		return this.code.equalsIgnoreCase(keyType.getCode());
	}
	
	public static CourseBehaviorType getProjectMemberTypeByCode (String code) {
		if (StrUtil.isEmpty(code))
		{
			return null;
		}
		switch (code) {
			case COURSE_CLICK : {
				return CLICK ;
			}
			case COURSE_PLAY : {
				return PLAY;
			}
			case CODE_LIKE : {
				return LIKE;
			}
			default: {
				throw new IllegalArgumentException("课程状态类型异常");
			}
		}
	}
}
