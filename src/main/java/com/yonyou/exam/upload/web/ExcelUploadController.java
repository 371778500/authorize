package com.yonyou.exam.upload.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.appbase.util.Logger;
import com.yonyou.appbase.util.StrUtil;
import com.yonyou.appbase.web.BaseController;
import com.yonyou.exam.upload.service.ExcelUploadService;
import com.yonyou.exam.upload.service.Plupload;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.upesn.service.file.NasService;
/**
 * 试题上传    Excel模板导入
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/upload")
public class ExcelUploadController extends BaseController{
	
	private final Log logger = Logger.getLogger(getClass());
	
	@Autowired
	private ExcelUploadService excelUploadService;
	
	/**
	 * Excel模板试题上传
	 * @param file
	 * @param pk_exam_paper_main
	 * @param plupload
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload")
	public @ResponseBody Object upload(@RequestParam MultipartFile file, @RequestParam(value = "pk_exam_paper_main") String pk_exam_paper_main,
			Plupload plupload, Model model, ServletRequest request) {
		Map<String,Object> result = new HashMap<String,Object>();
		plupload.setRequest(request);//手动传入Plupload对象HttpServletRequest属性
		String fileDir = request.getServletContext().getRealPath("") + File.separator + "doc";//文件保存的文件夹
        File dir = new File(fileDir);
        if(!dir.exists()){
            dir.mkdirs();//可创建多级目录，而mkdir()只能创建一级目录
        }
        //开始上传文件
        try {
        	File targetFile = excelUploadService.upload(plupload, dir, plupload.getName());
        	excelUploadService.excelImport(targetFile, pk_exam_paper_main);
		} catch (Exception e) {
			result.put("flag", "error");
			result.put("msg", "导入附件错误!");
			e.printStackTrace();
		}
        return result;
	}
}
