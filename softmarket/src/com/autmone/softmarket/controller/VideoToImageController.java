package com.autmone.softmarket.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.autmone.softmarket.util.ErrorHandle;
import com.autmone.softmarket.util.ImageUtil;
import com.autmone.softmarket.util.JsonUtils;
import com.autmone.softmarket.util.Util;

@Controller 
@RequestMapping("/video")
public class VideoToImageController {

	private String basePath = "/data001/apps/Tomcat/webapps/softmarket";
	private String fileSrc = basePath + "/video";
	private String imgFileSrc = basePath + "/image";

//	String basePath = "f:";
//	String fileSrc = basePath ;
//	String imgFileSrc = basePath ;

	@RequestMapping(value="/uploadVideo",method=RequestMethod.POST)
	public void uploadVideo(HttpServletRequest request, HttpServletResponse response) {
		
		// 临时目录
		String url = Thread.currentThread().getContextClassLoader().getResource("../../temp").getPath();
		File tempPath = new File(Util.strFromGet(url));
		if (!tempPath.exists()) {
			tempPath.mkdir();
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		factory.setRepository(tempPath);

		ServletFileUpload upload = new ServletFileUpload(factory);
		// maximum size before a FileUploadException will be thrown
		upload.setSizeMax(1024 * 1024 * 30);

		try {
			
			long fileName = 0L;
			String width = "350";
			String height = "240";
			List fileItems = upload.parseRequest(request);
			for (Iterator iter = fileItems.iterator(); iter.hasNext();) {
				// 获得序列中的下一个元素
				FileItem item = (FileItem) iter.next();
				// 判断是文件还是文本信息
				// 是普通的表单输入域
				if (item.isFormField()) {
					if("width".equals(item.getFieldName())) {
						width = item.getString();
					} else if("height".equals(item.getFieldName())) {
						height = item.getString();
					}
				}
				// 是否为文件
				if (!item.isFormField() && item.getContentType() != null) {
					// 上传文件的名称和完整路径
					long size = item.getSize();
					// 判断是否选择了文件
					if (size == 0) {
						continue;
					}
					
					fileName = new Date().getTime();
					
					File filePath = new File(Util.strFromGet(fileSrc));
					if (!filePath.exists()) {
						filePath.mkdirs();
					}
					File f = new File(fileSrc + File.separator + fileName);
					if(!f.exists()) {
						f.createNewFile();
					}
					
//					BufferedInputStream bis = null;
//			        BufferedOutputStream bos = null;
//			        bis = new BufferedInputStream(item.getInputStream());
//					bos = new BufferedOutputStream(new FileOutputStream(f));
			        InputStream inputStream = item.getInputStream();
					OutputStream os = new FileOutputStream(fileSrc + File.separator + fileName);
					int length = 1024;
					byte[] b = new byte[length];
		            while ((length=inputStream.read(b)) != -1)
		            {
		            	os.write(b, 0, length);
		            }
		            System.out.println(f.length());
//		            bos.flush();
//		            bis.close();
//		            bos.close();
		            os.flush();
		            os.close();
		            inputStream.close();
		            
				}
			}
			System.out.println(height + "==" + width);

			JSONObject json = new JSONObject();
			json.put("fileName", fileName);

			try {
				executeCodecs("/usr/local/bin/ffmpeg", fileSrc + "/" + fileName, fileSrc + "/" + fileName + "_1.flv");
				System.out.println(new Date().getTime() + " ==视频转化完成=="+fileSrc + "/" + fileName+"-->"+fileSrc + "/" + fileName + "_1.flv");
				Thread.sleep(2000);

				screenImage("/usr/local/bin/ffmpeg", fileSrc + "/" + fileName + "_1.flv", imgFileSrc + "/" + fileName + ".png", width, height);

				System.out.println(new Date().getTime() + " == 截图完成 == " + fileSrc + "/" + fileName + "_1.flv -->" +imgFileSrc + "/" + fileName + ".png");
			} catch (Exception e) {
				e.printStackTrace();
			}
//			
            
//            File img = new File(imgFileSrc + File.separator + fileName + ".png");
//            img.delete();
			
			String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, json);
			Util.returnMsg(response, jsonStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/getImage",method=RequestMethod.GET)
	public void getImage(HttpServletRequest request, HttpServletResponse response) {

		String fileName = request.getParameter("fileName");
		String width = request.getParameter("width");
		String height = request.getParameter("height");

    	if(Util.isEmpty(width)) {
    		width = "350";
    	}
    	if(Util.isEmpty(height)) {
    		height = "240";
    	}
    	System.out.println(width + "==" + height);
		
		BufferedImage back = ImageUtil.compositeImage(fileName, imgFileSrc + "/" + fileName + ".png", Integer.parseInt(width)/2 - 44, Integer.parseInt(height), 88, 88);
		
		try {
			OutputStream os = new FileOutputStream(imgFileSrc + "/" + fileName + "_1.png");
			ImageIO.write(back, "png", os);
	        os.flush();
	        os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonStr = JsonUtils.mapToJson(ErrorHandle.SUCCESS, ErrorHandle.SUCCESS_STR, "");
		Util.returnMsg(response, jsonStr);
	}
	
	@RequestMapping(value="/getVideo",method=RequestMethod.GET)
	public void getVideo(HttpServletRequest request, HttpServletResponse response) {
		
	}
	
	/**
     * 视频转码
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @return
     * @throws Exception
     */
    private boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath) throws Exception {

    	// 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); // 添加转换工具路径
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
        convert.add("-qscale");     //指定转换的质量
        convert.add("6");
        convert.add("-ab");        //设置音频码率
        convert.add("64");
        convert.add("-ac");        //设置声道数
        convert.add("2");
        convert.add("-ar");        //设置声音的采样频率
        convert.add("22050");
        convert.add("-r");        //设置帧频
        convert.add("24");
        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            
        	builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();
        	
        } catch (Exception e) {
            mark = false;
            e.printStackTrace();
        }
        return mark;
    }
    
    /**
     * 
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    要截图的视频源文件
     * @param mediaPicPath	添加截取的图片的保存路径
     * @param width			截图的宽
     * @param height		截图的高
     * @return
     */
    private boolean screenImage(String ffmpegPath, String upFilePath, String mediaPicPath, String width, String height) {
    	
    	// 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 要截图的视频源文件
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("1"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(width + "*" + height); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        ProcessBuilder builder = new ProcessBuilder();
        try {
        	
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        	return false;
        }
        return true;
    }
}
