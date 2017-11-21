
package com.yonyou.exam.paper.entity;

import com.yonyou.iuap.persistence.jdbc.framework.annotation.Entity;
import com.yonyou.iuap.persistence.bs.jdbc.meta.model.MDEnum;
import com.yonyou.metadata.spi.EnumItem;
/**
 * <b>此处简要描述此枚举的功能 </b>
 * <p>
 *   此处添加该枚举的描述信息
 * </p>
 *  创建日期:2016-12-13
 * @author 
 * @version 
 */
@Entity(namespace = "upesn",name = "using_status" )
public class Using_status extends MDEnum{

	public Using_status(EnumItem enumvalue){
		super(enumvalue);
	}

	public static final Using_status 未用 = MDEnum.valueOf(Using_status.class, String.valueOf("0"));
	public static final Using_status 已启用 = MDEnum.valueOf(Using_status.class, String.valueOf("1"));
	
}