package com.searchproject.pubmed;

import com.searchproject.pubmed.config.RedisIndexConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PubmedApplicationTests {
@Autowired
    RedisIndexConfig redisIndexConfig;
    @Test
    public void contextLoads() {
        System.out.println( redisIndexConfig.getMap().get("VISUAL_Expert_Coop"));

    }

}
