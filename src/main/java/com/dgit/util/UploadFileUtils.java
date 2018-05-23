package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.jws.soap.SOAPBinding.Style;
import javax.swing.ImageIcon;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	// return : /년/월/일/파일이름
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException{
		UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 설정할 때 사용
		String savedName = uid.toString() + "_" + originalName;
		String savedPath = calcPath(uploadPath);
		// uploadPath - c://zzz/upload
		// savedPath - /2018/05/23
		// savedName - uuid_aaa.jpg
		File target = new File(uploadPath + savedPath + "/" + savedName);  // uploadPath 서버주소
		FileCopyUtils.copy(fileData, target);
		
		//thumbnail파일 만들어서 경로 리턴
		String thumbPath = makeThumbnail(uploadPath, savedPath, savedName);
		
		return thumbPath; // savedPath + "/" + savedName;
	}
	
	
	
	
	private static String calcPath(String uploadPath){
		// 년, 월, 일 폴더 만들기
		Calendar cal = Calendar.getInstance();
		String yearPath = "/" + cal.get(Calendar.YEAR);
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1); // /2018/05
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
	}
	
	public static void makeDir(String uploadPath, String...paths){
		for(String path : paths){
			File dirPath = new File(uploadPath + path);
			if(dirPath.exists() == false){
				dirPath.mkdir();
			}
			
		}
	}
	
	//upload : c//zzz/upload
	//path : /년/월/일
	//filename : uuid_aaa.jpg
	private static String makeThumbnail(String uploadPath, String path, String filename) throws IOException{
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, filename));  //원본이미지 데이터를 뽑음
		
		//FIT_TO_HEIGHT : 썸네일 이미지의 높이를 뒤의 100PX로 동일하게 만듬.		
		BufferedImage destImg = Scalr.resize(sourceImg,  Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT ,100);  //작은이미지로 만듬(데이터로만 존재)
		
		
		//thumbnail 파일 이름 : "s_"붙임		
		String thumbnailName = uploadPath + path +"/" + "s_" + filename;
		
		File newFile = new File(thumbnailName);
		//확장자 찾기
		String formatName = filename.substring(filename.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		String savedName = thumbnailName.substring(uploadPath.length());
		
		return savedName;  // /년/월/일/s_uuid_aaa.jpg		
	}
	
	public static void deleteFile(String uploadPath, String filename){
		
		//선생님 코드
		
		File file = new File(uploadPath + filename);
		//썸네일 삭제
		if(file.exists()){
			file.delete();
		}
		
		String front = filename.substring(0, 12);
		String end = filename.substring(14);
		String originalName = front + end;
		
		File file2 = new File(uploadPath + originalName);
		//원본 삭제
		if(file2.exists()){
			file2.delete();
		}
		
	}
}



























