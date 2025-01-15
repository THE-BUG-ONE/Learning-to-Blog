package com.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        try {
            //测试连接
            factory.getConnection();
        } catch (RedisConnectionFailureException e) {
            log.warn("未发现redis服务");
        }

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        //序列化为String
        StringRedisSerializer strSerializer = new StringRedisSerializer();
        //设置key,value的序列化规则
        stringRedisTemplate.setKeySerializer(strSerializer);
        stringRedisTemplate.setValueSerializer(strSerializer);
        //设置hashKey,hashValue的序列化规则
        stringRedisTemplate.setHashKeySerializer(strSerializer);
        stringRedisTemplate.setHashValueSerializer(strSerializer);
        //设置redis链接工厂
        stringRedisTemplate.setConnectionFactory(factory);

        return stringRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        try {
            //测试连接
            factory.getConnection();
        } catch (RedisConnectionFailureException e) {
            log.warn("未发现redis服务");
        }

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //序列化为String
        StringRedisSerializer strSerializer = new StringRedisSerializer();
        //序列化为FastJson
        FastJson2RedisSerializer<Object> fasSerializer = new FastJson2RedisSerializer<>(Object.class);
        //设置key,value的序列化规则
        redisTemplate.setKeySerializer(strSerializer);
        redisTemplate.setValueSerializer(fasSerializer);
        //设置hashKey,hashValue的序列化规则
        redisTemplate.setHashKeySerializer(strSerializer);
        redisTemplate.setHashValueSerializer(fasSerializer);
        //设置redis链接工厂
        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }
}

