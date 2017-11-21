package com.yonyou.upesn.service.file;

import org.junit.Test;
import org.springframework.stereotype.Service;

@Service
public class NasService {
	
	/**
	 * 获取文件根路径
	 * @return
	 */
	public String getFileStorageRootDir(){
		String rootPath=getClass().getResource("/").getFile().toString();
		rootPath=rootPath.substring(1);
		int index=rootPath.indexOf("WEB-INF");
		rootPath=rootPath.substring(0, index)+"doc";
		return rootPath;
    }
	@Test
	public void test(){
		String rootPath=getClass().getResource("/").getFile().toString();
		rootPath=rootPath.substring(1);
		int index=rootPath.indexOf("WEB-INF");
		rootPath=rootPath.substring(0, index)+"img/";
	}
}
