package com.searchproject.pubmed.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class RedisDao {
    @Autowired
    private StringRedisTemplate redisTemplateA;
    @Autowired
    @Qualifier("redisTemplateB")
    private StringRedisTemplate redisTemplateB;

    /**
     * 动态切换数据库
     */
//    public void setDataBase(int num) {
//        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
//        if (connectionFactory != null && num != connectionFactory.getDatabase()) {
//            connectionFactory.setDatabase(num);
//            this.redisTemplate.setConnectionFactory(connectionFactory);
//            connectionFactory.resetConnection();
//            log.debug("切换成功");
//        }
//    }
    public Set<String> getAidSet(String fullname){
//        setDataBase(7);

        return redisTemplateA.opsForSet().members(fullname);
    }
    public Set<String> getPmidSet(String entity){
//        setDataBase(19);
        return redisTemplateB.opsForSet().members(entity);
    }

}
