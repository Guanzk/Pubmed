package com.searchproject.pubmed;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(locations = {"classpath:application.properties"})
public class SpringBootRedisApplicationTests {

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
//	@Autowired
//	private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Test
    public void testString() {
        //strRedisTemplate.opsForValue().set("strKey", "zwqh");
        System.out.println(strRedisTemplate.opsForValue().get("7262432"));
    }

    @Test
    public void testSerializable() {
//        UserEntity user=new UserEntity();
//        user.setId(1L);
//        user.setUserName("朝雾轻寒");
//        user.setUserSex("男");
        //serializableRedisTemplate.opsForValue().set("user", user);
        //UserEntity user2 = (UserEntity) serializableRedisTemplate.opsForValue().get("user");
        //System.out.println("user:"+user2.getId()+","+user2.getUserName()+","+user2.getUserSex());
    }

}
