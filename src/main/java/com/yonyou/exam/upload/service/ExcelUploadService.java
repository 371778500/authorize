package com.yonyou.exam.upload.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yonyou.appbase.util.StrUtil;
import com.yonyou.exam.paper.entity.ExamQuestionVo;
import com.yonyou.exam.paper.repostitory.QuestionDAO;

@Service
public class ExcelUploadService {
	
	@Autowired
	private QuestionDAO questionDAO;
	
	public File upload(Plupload plupload, File pluploadDir, String fileName){
		File targetFile = new File(pluploadDir+File.separator+fileName);
        int chunks = plupload.getChunks();//用户上传文件被分隔的总块数
        int nowChunk = plupload.getChunk();//当前块，从0开始
        //这里Request请求类型的强制转换可能出错，配置文件中向SpringIOC容器引入multipartResolver对象即可。
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)plupload.getRequest();
        //调试发现map中只有一个键值对
        MultiValueMap<String,MultipartFile> map = multipartHttpServletRequest.getMultiFileMap();
        if(map != null){
            try{
                Iterator<String> iterator = map.keySet().iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    List<MultipartFile> multipartFileList = map.get(key);
                    for(MultipartFile multipartFile:multipartFileList){//循环只进行一次
                        plupload.setMultipartFile(multipartFile);//手动向Plupload对象传入MultipartFile属性值
                        if(chunks>1){//用户上传资料总块数大于1，要进行合并
                            File tempFile = new File(pluploadDir.getPath()+File.separator+multipartFile.getName());
                            //第一块直接从头写入，不用从末端写入
                            savePluploadFile(multipartFile.getInputStream(),tempFile,nowChunk==0?false:true);
                            if(chunks-nowChunk==1){//全部块已经上传完毕，此时targetFile因为有被流写入而存在，要改文件名字
                                tempFile.renameTo(targetFile);
                            }
                        } else{
                            //只有一块，就直接拷贝文件内容
                        	if (targetFile.exists()) {
                        		targetFile.delete();
                        	}
                            multipartFile.transferTo(targetFile);
                        }
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return targetFile;
    }
	
    private void savePluploadFile(InputStream inputStream,File tempFile,boolean flag){
        OutputStream outputStream = null;
        try {
            if (flag == false) {
                //从头写入
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
            } else {
                //从末端写入
                outputStream = new BufferedOutputStream(new FileOutputStream(tempFile,true));
            }
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = (inputStream.read(bytes)))>0){
                outputStream.write(bytes,0,len);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                outputStream.close();
                inputStream.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
   

	/**
	 * 解析Excel，试卷信息保存到数据库
	 * @param dir
	 * @param pk_exam_paper_main
	 */
	public void excelImport(File dir, String pk_exam_paper_main) {
		// TODO 自动生成的方法存根
		try{
			List<ExamQuestionVo> examquestionList = new ArrayList<ExamQuestionVo>();
			ExamQuestionVo examQuestionVo = null;
			List<List<Object>> list = ReadExcel.readExcel(dir, 0);
			for(List<Object> list2 : list){
				examQuestionVo = new ExamQuestionVo();
				examQuestionVo.setOptiontype(Integer.parseInt((String) list2.get(0)));//选项类型 1多选  0单选
				examQuestionVo.setQuestion_type((String) list2.get(1));//题型
				examQuestionVo.setQuestion_num(Integer.parseInt((String) list2.get(2)));//题号
				examQuestionVo.setQuestion_title((String) list2.get(3));//题干
				examQuestionVo.setOption_a((String) list2.get(4));//选项A
				examQuestionVo.setOption_b((String) list2.get(5));//选项B
				examQuestionVo.setOption_c((String) list2.get(6));//选项C
				examQuestionVo.setOption_d((String) list2.get(7));//选项D
				examQuestionVo.setOption_e((String) list2.get(8));//选项E
				examQuestionVo.setOption_f((String) list2.get(9));//选项F
				examQuestionVo.setScore(Integer.parseInt((String) list2.get(10)));//分数
				examQuestionVo.setRight_answer((String) list2.get(11));//答案
				examQuestionVo.setRight_answer_analysis((String) list2.get(12));//解析
				examQuestionVo.setPk_exam_paper_main(pk_exam_paper_main);//试卷管理主键
				examQuestionVo.setDr("0");//操作记录
				
				examquestionList.add(examQuestionVo);
				
			}
			questionDAO.insertList(examquestionList);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}

