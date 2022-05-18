package com.spingbootredis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class SpingbootRedisApplicationTests {
    //自己序列化配置
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name","老李");
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);

    }

    @Test
    void testUser(){

        redisTemplate.opsForValue().set("user:1",new user("老黄","google"));
        user o = (user) redisTemplate.opsForValue().get("user:1");
        System.out.println(o);
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ObjectMapper mapper=new ObjectMapper();
    //手动序列化测试
    @Test
    void testStringRedisTemplate() throws JsonProcessingException {
        user user=new user("王阳明","武当");
        String json = mapper.writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user:2",json);
        String s = stringRedisTemplate.opsForValue().get("user:2");
        user user1 = mapper.readValue(s, com.spingbootredis.user.class);
        System.out.println(user1);

    }

}
