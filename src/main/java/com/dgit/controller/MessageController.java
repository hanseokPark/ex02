package com.dgit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dgit.domain.MessageVO;
import com.dgit.service.MessageService;

@RequestMapping("/message")
@RestController
public class MessageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	MessageService service;
	
	
	
	// "{{aa:bb,aa2:bb2}}"
	@RequestMapping(value="",method=RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageVO vo){
		logger.info(vo.toString());
		
		ResponseEntity<String> entity = null;
		
		try{			
			service.addMessage(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public ResponseEntity<MessageVO> readMessage(String uid, int mno){
		logger.info("uid : " + uid +", mno : " +mno);
		
		ResponseEntity<MessageVO> entity = null;
		
		try{
			MessageVO vo = service.readMessage(uid, mno);
			
			entity = new ResponseEntity<>(vo, HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	} 
}
