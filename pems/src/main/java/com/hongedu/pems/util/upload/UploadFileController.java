package com.hongedu.pems.util.upload;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * 教师端上传文件处理层
 * @data 2017年8月18日 14:50:34
 * @author 王攀登
 * 
 */
@Controller
public class UploadFileController extends HttpServlet {  

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7831502961649255823L;

	/**
	 *上传处理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/upload")
	@ResponseBody
    public String upload(HttpServletRequest request, HttpServletResponse response){
		String responseStr="";  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
        //上传文件存放到项目目录中
        String root = request.getSession().getServletContext().getRealPath("/");
        String ctxPath= root+"/uploads/";  
        //上传问件存放到本地
//	        String ctxPath= "D:/uploads/";  
        //上传文件存放到服务器上
//	        String ctxPath= ""; 
        //创建文件夹  
        File file = new File(ctxPath);    
        if (!file.exists()) {    
            file.mkdirs();    
        }    
        String fileName = null;
        String path=null;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
            MultipartFile mf = entity.getValue();    
            fileName = mf.getOriginalFilename();  
            
            String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");// 返回一个随机UUID。
            String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            // 构成新文件名。
            String newFileName =uuid + (suffix!=null?suffix:"");
            File uploadFile = new File(ctxPath + newFileName);    
            try {  
                FileCopyUtils.copy(mf.getBytes(), uploadFile); 
                path ="/uploads/"+newFileName;
        } catch (IOException e) {  
            responseStr="上传失败";  
            e.printStackTrace();  
        }    
        }   
        return path;  
    }
	
}
