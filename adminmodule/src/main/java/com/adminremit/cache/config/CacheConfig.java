package com.adminremit.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.adminremit.masters.models.Currencies;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Currencies> redisCurrenciesTemplate() {
        RedisTemplate<String, Currencies> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    public RedisTemplate<String, Float> redisFxRateTemplate() {
        RedisTemplate<String, Float> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}