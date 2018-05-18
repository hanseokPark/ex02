package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.util.MediaUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	private String innerUploadPath = "resources/upload";
	
	@Resource(name="uploadPath")
	private String outerUploadPath;
	
	@RequestMapping(value="innerUpload", method=RequestMethod.GET)
	public String innerUploadForm(){
		return "innerUploadForm";
	}
	
	@RequestMapping(value="innerUpload", method=RequestMethod.POST)
	public String innerUploadResult(String writer, MultipartFile file, HttpServletRequest request, Model model) throws IOException{
		logger.info("writer : " + writer);
		logger.info("file : " + file.getOriginalFilename());
		
		
		String root_path = request.getRealPath("/");
		logger.info("root_path : " + root_path );
		
		//폴더 만들어줌
		File dirPath = new File(root_path + "/" + innerUploadPath);
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		
		UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 설정할 때 사용
		String savedName = uid.toString() + "_" + file.getOriginalFilename();
		File target = new File(root_path + "/" + innerUploadPath + "/" + savedName);
		FileCopyUtils.copy(file.getBytes(), target);
		
		
		model.addAttribute("writer", writer);
		model.addAttribute("filePath", innerUploadPath + "/" + savedName);		
		
		
		return "innerUploadResult";
	}
	
	
	@RequestMapping(value="innerMultiUpload", method=RequestMethod.GET)
	public String innerMultiUpload(){
		return "innerMultiUploadForm";
	}
	
	@RequestMapping(value="innerMultiUpload", method=RequestMethod.POST)
	public String innerMultiUploadResult(String writer, List<MultipartFile> files, HttpServletRequest request, Model model) throws IOException{
		logger.info("writer : " + writer);
		
		String root_path = request.getRealPath("/");
		logger.info("root_path : " + root_path );
		
		
		File dirPath = new File(root_path + "/" + innerUploadPath);
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		
		for(MultipartFile file : files){
			logger.info("filename : " + file.getOriginalFilename());
			logger.info("fileSize : " + file.getSize());
		}
		
		ArrayList<String> pathList = new ArrayList<>();
		for(MultipartFile file: files){
			UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 설정할 때 사용
			String savedName = uid.toString() + "_" + file.getOriginalFilename();
			File target = new File(root_path + "/" + innerUploadPath + "/" + savedName);
			FileCopyUtils.copy(file.getBytes(), target);
			
			pathList.add(savedName);
		}
		
		model.addAttribute("writer", writer);
		model.addAttribute("listPath",pathList);
	
		
		return "innerMultiUploadResult";
	}
	

	
	// =====================================================   서버 외부 업로드 
	@RequestMapping(value="outerUpload", method=RequestMethod.GET)
	public String outerUploadForm(){
		
		return "uploadForm";
	}
	
	
	@RequestMapping(value="outerUpload", method=RequestMethod.POST)
	public String outerUploadResult(String writer, MultipartFile file, Model model) throws IOException{
		logger.info("writer : " + writer);
		logger.info("file : " + file.getOriginalFilename());
		logger.info("outerUploadPath : " + outerUploadPath);
		
	/*	File dirPath = new File("c:/zzz");
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		File dirPath = new File("c:/zzz/upload");
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		*/
		
		
		
		UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 설정할 때 사용
		String savedName = uid.toString() + "_" + file.getOriginalFilename();
		File target = new File(outerUploadPath + "/" + savedName);
		FileCopyUtils.copy(file.getBytes(), target);
		
		model.addAttribute("writer", writer);
		model.addAttribute("fileName", savedName);
		
		return "uploadResult";
	}
	
	
	//@ResponseBody 데이터만 가는경우
	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displyFile(String filename) throws Exception{
		ResponseEntity<byte[]> entity = null;
		
		InputStream in = null;
		
		logger.info("[displayFile] filename : " + filename);
		
		try{
			
			String format = filename.substring(filename.lastIndexOf(".") +1);  //확장자만
			MediaType mType = MediaUtils.getMediaType(format);
			HttpHeaders headers = new  HttpHeaders();
			headers.setContentType(mType);
			
			in = new FileInputStream(outerUploadPath + "/" + filename);
			// IOUtils 바이트 배열로 뽑아줌
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
		
		return entity;
	}
	
	
	//드래그로 파일 업로드	
	@RequestMapping(value="dragUpload", method=RequestMethod.GET)
	public String uploadDragForm(){
		
		return "uploadDragForm";
	}
	
	@ResponseBody
	@RequestMapping(value="dragUpload", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> dragUploadResult(String writer, List<MultipartFile> files) throws IOException{
		logger.info("writer : " + writer);
		
		ResponseEntity<Map<String, Object>> entity= null;
				
		try {
			List<String> list = new ArrayList<>();
			for(MultipartFile file : files){
				logger.info("file : " + file.getOriginalFilename());
				
				UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 설정할 때 사용
				String savedName = uid.toString() + "_" + file.getOriginalFilename();
					File target = new File(outerUploadPath + "/" + savedName);
					FileCopyUtils.copy(file.getBytes(), target);
					list.add(savedName);
				}
			
					Map<String, Object> map = new HashMap<>();
					map.put("result", "success");
					map.put("listFile", list);
			
					entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Map<String, Object> map = new HashMap<>();
				map.put("result", "fail");
				entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
				
			}
		
		return entity;
		
	}
	
	
	
	
	
	@RequestMapping(value="previewUpload", method=RequestMethod.GET)
	public String previewForm(){
		
		return "previewForm";
	}
	
	
	
	@RequestMapping(value="previewUpload", method=RequestMethod.POST)
	public String previewResult(String writer, MultipartFile file, Model model) throws IOException{
		logger.info("writer : " + writer);
		logger.info("file : " + file.getOriginalFilename());
		
		UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 설정할 때 사용
		String savedName = uid.toString() + "_" + file.getOriginalFilename();
		File target = new File(outerUploadPath + "/" + savedName);
		FileCopyUtils.copy(file.getBytes(), target);
		
		
		model.addAttribute("writer", writer);
		model.addAttribute("file", savedName);
		
		return "previewResult";
	}
}






