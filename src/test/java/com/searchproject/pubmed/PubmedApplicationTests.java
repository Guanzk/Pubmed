package com.searchproject.pubmed;

import com.searchproject.pubmed.config.RedisIndexConfig;
import com.searchproject.pubmed.service.SearchAi;
import com.searchproject.pubmed.service.SearchPubmed;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PubmedApplicationTests {
    @Autowired
    RedisIndexConfig redisIndexConfig;
    @Test
    public void contextLoads() {
        System.out.println( redisIndexConfig.getMap().get("VISUAL_Expert_Coop"));

    }
    @Autowired
    SearchAi searchAi;
    @Test
    public void testPubmed(){
        log.info(searchAi.search("deeplearning"));
    }

}
