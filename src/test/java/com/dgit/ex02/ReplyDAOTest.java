package com.dgit.ex02;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.Criteria;
import com.dgit.domain.ReplyVO;
import com.dgit.persistence.ReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"file:src/main/webapp/Web-inf/spring/**/*.xml"})
public class ReplyDAOTest {
	
	@Autowired
	ReplyDAO dao;
	
	//@Test
	public void testlist() throws Exception{		
		dao.list(446);
	}
	
	//@Test
	public void testcreate() throws Exception{
		ReplyVO vo = new ReplyVO();
		vo.setBno(446);
		vo.setReplyer("user00");
		vo.setReplytext("댓글1");
		
		dao.create(vo);
	}
	//@Test
	public void testupdate() throws Exception{
		ReplyVO vo = new ReplyVO();
		vo.setReplytext("댓글1수정");
		vo.setRno(2);
		
		dao.update(vo);
	}
	//@Test
	public void testdelete() throws Exception{		
		dao.delete(2);
	}
	
	//@Test
	public void testListPage() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(1);
		dao.listPage(446, cri);
	}
}
