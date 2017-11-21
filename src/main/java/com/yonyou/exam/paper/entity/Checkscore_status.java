
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
@Entity(namespace = "upesn",name = "checkscore_status" )
public class Checkscore_status extends MDEnum{

	public Checkscore_status(EnumItem enumvalue){
		super(enumvalue);
	}

	public static final Checkscore_status 不可以查看分数 = MDEnum.valueOf(Checkscore_status.class, String.valueOf("0"));
	public static final Checkscore_status 可以查看分数 = MDEnum.valueOf(Checkscore_status.class, String.valueOf("1"));
	
}