package com.yonyou.upesn.web.back.file;

import java.io.File;

import javax.servlet.ServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.appbase.util.Logger;
import com.yonyou.appbase.util.RandomGUID;
import com.yonyou.appbase.web.BaseController;
import com.yonyou.upesn.service.file.FileUploadService;
import com.yonyou.upesn.service.file.NasService;
import com.yonyou.upesn.service.file.Plupload;

@Controller
@RequestMapping(value = "/file")
public class FileUploadController extends BaseController {
	
	private final Log logger = Logger.getLogger(getClass());
	
	@Autowired
	private NasService nasService;
	@Autowired
	private FileUploadService fileupload;
	
	/**
     * 文件上传到服务器硬盘并新增资产到数据库
     * 文件存储绝对路径,会是一个文件夹，项目相应Servlet容器下的"doc"文件夹，还会以课程类型pk当作第二级路径，文件pk当作第三级路径
     */
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody void upload(@RequestParam MultipartFile file,@RequestParam(value = "coursetypeid") String coursetypeid,
			@RequestParam(value = "fileid") String fileid,Plupload plupload,Model model, ServletRequest request) {
    	 String rootPath = request.getServletContext().getRealPath("/") + "doc";
    	 //nasService.getFileStorageRootDir()暂时获取有问题,改用rootPath
    	 //String FileDir =nasService.getFileStorageRootDir()+File.separator+coursetypeid+File.separator+fileid;//文件保存的文件夹
    	 String FileDir =rootPath+File.separator+coursetypeid+File.separator+fileid;//文件保存的文件夹
         plupload.setRequest(request);//手动传入Plupload对象HttpServletRequest属性
//    	 String originalFilename=file.getOriginalFilename();
//		 String contentType = file.getContentType();
	     File dir = new File(FileDir);
	     if(!dir.exists()){
	         dir.mkdirs();//可创建多级目录，而mkdir()只能创建一级目录
	     }
         //开始上传文件
         try {
        	 fileupload.upload(plupload, dir);
//			projectAssetService.saveAsset(pk_projectmilestone,pk_project,pkasset,isapply,plupload,FileDir,pkuser);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件出错!",e);
		}
    }
    
    

}
