package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MessageVO;

@Repository
public class MessageDAOImpl implements MessageDAO {
	@Autowired
	SqlSession session;
	

	private static final String namespace = "com.dgit.mapper.MessageMapper";
	
	@Override
	public void create(MessageVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace + ".create", vo);
	}

	@Override
	public MessageVO readMessage(int mid) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace + ".readMessage", mid);
	}

	@Override
	public void updateState(int mid) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace + ".updateState", mid);
	}

}
