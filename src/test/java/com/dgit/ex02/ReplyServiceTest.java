package com.dgit.ex02;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.ReplyVO;
import com.dgit.service.ReplyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/Web-inf/spring/**/*.xml"})
public class ReplyServiceTest {
	
	@Autowired
	ReplyService service;
	
	//@Test
	public void testAddReply() throws Exception{
		ReplyVO vo = new ReplyVO();
		vo.setBno(446);
		vo.setReplyer("user00");
		vo.setReplytext("댓글1");		
		
		service.addReply(vo);
	}
	
	@Test
	public void testlistReply(){
		/*dao.list(446);*/
		
				
	}
	
	

}
