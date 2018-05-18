package com.dgit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgit.domain.MessageVO;
import com.dgit.persistence.MessageDAO;
import com.dgit.persistence.PointDAO;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageDAO messageDao;
	
	@Autowired
	PointDAO pointDao;
	
	@Transactional
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		// 메시지를 남긴 사용자는 10포인트가 추가된다.
		messageDao.create(vo);
		pointDao.updatePoint(vo.getSender(), 10);
	}
	
	@Transactional
	@Override
	public MessageVO readMessage(String uid, int mno) throws Exception {
		// 남겨진 메시지를 읽으면 5포인트가 추가된다.
		
		messageDao.updateState(mno);
		
		pointDao.updatePoint(uid, 5);
		
		return messageDao.readMessage(mno);
	}

}
