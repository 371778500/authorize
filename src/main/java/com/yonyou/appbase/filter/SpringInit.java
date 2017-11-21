package com.yonyou.appbase.filter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class SpringInit implements ServletContextListener {
    

    private static WebApplicationContext applicationContext;
    
    /**
	 * @param cxt 要设置的 applicationContext
	 */
	public static void setApplicationContext(
			XmlWebApplicationContext cxt) {
		SpringInit.applicationContext = (WebApplicationContext) cxt;
	}

	public SpringInit() {
        super();
    }
    
    public void contextInitialized(ServletContextEvent event) {
    	applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
    }
    

    public void contextDestroyed(ServletContextEvent event) {
    }
    
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    
}
