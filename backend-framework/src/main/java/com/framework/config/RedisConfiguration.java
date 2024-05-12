package com.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //序列化为String
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置String类型的key设置序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置Hash类型的key设置序列化器
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //设置redis链接Lettuce工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

        return redisTemplate;
    }
}
