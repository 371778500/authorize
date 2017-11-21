package com.yonyou.appbase.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
	private static Log logger = null ;
	public  synchronized static Log getLogger(Class cls){
		if (null == logger)
		{
			logger = LogFactory.getLog(cls);
		}
		return logger;
		
	}
	
}
