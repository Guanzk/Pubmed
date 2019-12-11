package com.searchproject.pubmed.test;

import com.searchproject.pubmed.Bean.Paper;
import com.searchproject.pubmed.dao.PaperDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDao {
    @Autowired
    PaperDao dao;

    @Test
    public void test(){
        Paper p=new Paper();
        p.setPMID("122545");
        Example<Paper>ex=Example.of(p);
        List<Paper> ps=dao.findAll(ex);
        System.out.println(ps);
    }
}
